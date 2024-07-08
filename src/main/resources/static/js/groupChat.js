let stompClient = null;

/**
 * WebSocket 연결 함수
 */
function connectWebSocket() {
    const socket = new SockJS('/ws-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        joinRoom(currentStudyIdx);
    });
}

/**
 * 특정 스터디 방 참여 함수
 * @param {number} studyIdx - 스터디 인덱스
 */
function joinRoom(studyIdx) {
    stompClient.subscribe('/group/chat/room/' + studyIdx, function (message) {
        showMessage(JSON.parse(message.body));
    });

    const joinMessage = {
        studyIdx: studyIdx,
        userIdx: currentUserIdx
    };
    stompClient.send("/app/chat/enterRoom", {}, JSON.stringify(joinMessage));
}

/**
 * 채팅 메시지 전송 함수
 */
function sendMessage() {
    const messageInput = document.getElementById('message-input');
    const messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        const chatMessage = {
            studyIdx: currentStudyIdx,
            userIdx: currentUserIdx,
            chatContent: messageContent
        };
        stompClient.send("/app/chat/sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
}

/**
 * 수신한 메시지를 화면에 표시하는 함수
 * @param {Object} message - 수신한 메시지 객체
 */
function showMessage(message) {
    const messageArea = document.getElementById('chat-messages');
    const messageElement = document.createElement('div');
    messageElement.className = 'message ' + (message.userIdx == currentUserIdx ? 'sent' : 'received');

    let messageContent = '';
    if (message.userIdx != currentUserIdx) {
        messageContent += `<div class="sender">${message.userNickName || '알 수 없음'}</div>`;
    }
    messageContent += `<div>${message.chatContent}</div>`;

    messageElement.innerHTML = messageContent;
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

// 문서 로드 완료 시 실행
document.addEventListener('DOMContentLoaded', function () {
    connectWebSocket();

    const sendButton = document.getElementById('send-button');
    const messageInput = document.getElementById('message-input');

    sendButton.addEventListener('click', sendMessage);

    messageInput.addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            sendMessage();
        }
    });
});