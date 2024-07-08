// WebSocket 연결을 위한 전역 변수
var stompClient = null;
var chatIdx = null;
var userIdx = null;

/**
 * WebSocket 연결 설정
 */
function connect() {
    // SockJS를 사용하여 WebSocket 연결 생성
    var socket = new SockJS('/ws-endpoint');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        // chatIdx와 userIdx 값을 가져옴
        chatIdx = getChatIdxFromUrl(); // URL에서 chatIdx를 추출하는 함수 (아래에 정의)
        userIdx = getUserIdxFromSession(); // 세션에서 userIdx를 가져오는 함수 (아래에 정의)

        // 채팅방 구독
        stompClient.subscribe('/personal/chat/room/' + chatIdx, function (chatMessage) {
            showMessage(JSON.parse(chatMessage.body));
        });

        // 채팅방 입장
        enterChatRoom();
    });
}

/**
 * URL에서 chatIdx를 추출하는 함수
 */
function getChatIdxFromUrl() {
    // 현재 URL에서 chatIdx를 추출하는 로직
    // 예: URL이 "/chat/room/123"이라면 "123"을 반환
    var pathArray = window.location.pathname.split('/');
    return pathArray[pathArray.length - 1];
}

/**
 * 세션에서 userIdx를 가져오는 함수
 */
function getUserIdxFromSession() {
    // 서버에서 설정한 세션 값을 가져오는 로직
    // 이는 서버 구현에 따라 달라질 수 있음
    // 예: 쿠키나 로컬 스토리지에서 값을 가져오거나,
    // 페이지 로드 시 서버에서 전달받은 값을 사용
    return document.getElementById('userIdxInput').value; // 예시: hidden input 필드에서 값을 가져옴
}

/**
 * 채팅방 입장
 */
function enterChatRoom() {
    // 입장 메시지 전송
    stompClient.send("/app/chat/enter/" + chatIdx, {},
        JSON.stringify({'userIdx': userIdx, 'chatIdx': chatIdx}));
}

/**
 * 메시지 전송
 */
function sendMessage() {
    var messageContent = document.getElementById('message').value;
    if (messageContent && stompClient) {
        var chatMessage = {
            userIdx: userIdx,
            chatIdx: chatIdx,
            chatContent: messageContent
        };
        stompClient.send("/app/chat/send/" + chatIdx, {}, JSON.stringify(chatMessage));
        document.getElementById('message').value = '';
    }
}

/**
 * 메시지 화면에 표시
 */
function showMessage(message) {
    var messageArea = document.getElementById('messageArea');
    var messageElement = document.createElement('div');
    messageElement.innerHTML = message.chatContent;
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight; // 스크롤을 항상 아래로 유지
}

// 페이지 로드 시 실행
document.addEventListener('DOMContentLoaded', function () {
    connect();

    // 메시지 전송 버튼 클릭 이벤트
    document.getElementById('sendButton').addEventListener('click', sendMessage);

    // 엔터 키 입력 이벤트
    document.getElementById('message').addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            sendMessage();
        }
    });
});