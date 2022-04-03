package hexlet.code;

import io.javalin.Javalin;

public class App {

    private static Javalin getApp() {
        Javalin app = Javalin.create(config -> config.enableDevLogging());
        app.before(ctx -> ctx.attribute("ctx", ctx));
        return app;
    }

    private static int getPort() {
        final int portDefault = 5000;
        String port = System.getenv("PORT");
        if (port != null) {
            return Integer.parseInt(port);
        }
        return portDefault;
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(getPort());
        app.get("/", ctx -> ctx.result("Hello World"));
    }

}
