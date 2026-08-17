package com.example;

public final class SuppressedDiagnostic {

    private SuppressedDiagnostic() {
    }

    public static String runAndDescribe() {
        try (FailingResource resource = new FailingResource()) {
            resource.use();
            return "completed";
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

    private static final class FailingResource implements AutoCloseable {
        void use() throws Exception {
            throw new Exception("body failure");
        }

        @Override
        public void close() throws Exception {
            throw new Exception("close failure");
        }
    }
}
