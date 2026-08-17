package com.example;

public final class SuppressedDiagnostic {

    private SuppressedDiagnostic() {
    }

    public static String runAndDescribe() {
        try (FailingResource resource = new FailingResource()) {
            resource.use();
            return "completed";
        } catch (Exception exception) {
            StringBuilder diagnostic = new StringBuilder(exception.getMessage());
            for (Throwable suppressed : exception.getSuppressed()) {
                diagnostic.append("; suppressed=").append(suppressed.getMessage());
            }
            return diagnostic.toString();
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
