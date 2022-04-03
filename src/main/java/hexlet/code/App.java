package hexlet.code;

import io.javalin.Javalin;

public class App {

    public static Javalin getApp() {
        Javalin app = Javalin.create(config -> config.enableDevLogging());
        app.before(ctx -> ctx.attribute("ctx", ctx));
        return app;
    }

    public static void main(String[] args) {
        final int port = 5000;
        Javalin app = getApp();
        app.start(port);
        app.get("/", ctx -> ctx.result("Hello World"));
    }

}
