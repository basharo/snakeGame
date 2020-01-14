package asd.messages;

public class MessageTypeNotSupportedException extends Exception {

        public MessageTypeNotSupportedException(String message, Throwable throwable) {
            super(message, throwable);
        }

        public MessageTypeNotSupportedException(String message) {
            super(message);
        }

    }


