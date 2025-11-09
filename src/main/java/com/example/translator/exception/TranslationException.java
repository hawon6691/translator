package com.example.translator.exception;

/**
 * 번역 작업 중 발생하는 예외를 처리하는 커스텀 예외 클래스
 * RuntimeException을 상속받아 Unchecked Exception으로 동작
 */
public class TranslationException extends RuntimeException {

    /**
     * 메시지와 원인 예외를 모두 포함하는 생성자
     * @param message 사용자에게 보여줄 에러 메시지
     * @param cause 실제 발생한 원인 예외 (Google API 오류 등)
     */
    public TranslationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 메시지만 포함하는 생성자
     * @param message 사용자에게 보여줄 에러 메시지
     */
    public TranslationException(String message) {
        super(message);
    }
}
