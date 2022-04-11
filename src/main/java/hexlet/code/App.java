package hexlet.code;

import hexlet.code.controllers.RootController;
import hexlet.code.controllers.UrlController;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;

public class App {

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "5050");
        return Integer.parseInt(port);
    }

    private static String getMode() {
        return System.getenv().getOrDefault("APP_ENV", "development");
    }

    private static boolean isProduction() {
        return getMode().equals("production");
    }

    private static TemplateEngine getTemplateEngine() {
        // Create a templating engine instance
        TemplateEngine templateEngine = new TemplateEngine();
        // Adding dialects to it
        templateEngine.addDialect(new LayoutDialect());
        templateEngine.addDialect(new Java8TimeDialect());
        // Set up the template converter
        // to process templates in the /templates/ directory
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        // Adding a template converter to the template engine
        templateEngine.addTemplateResolver(templateResolver);

        return templateEngine;
    }

    // The method adds routes to the passed application
    private static void addRoutes(Javalin app) {
        // For a GET request to a route '/' will be executed welcome handler in the RootController
        app.get("/", RootController.welcome);

        // For a GET POST request to a route 'urls' and 'id' will be executed handlers in the UrlController
        app.routes(() -> {
            path("urls", () -> {
                get(UrlController.listUrls);
                post(UrlController.createUrl);
                path("{id}", () -> {
                    get(UrlController.showUrl);
                    post("/checks", UrlController.checkUrl);
                });
            });
        });
    }

    public static Javalin getApp() {
        // Create an application
        Javalin app = Javalin.create(config -> {
            if (!isProduction()) {
                // Enabling logging
                config.enableDevLogging();
            }
            config.enableWebjars();
            // Connect the configured template engine to the framework
            JavalinThymeleaf.configure(getTemplateEngine());
        });
        // Adding routes to the application
        addRoutes(app);

        // The 'before' handler runs before each request set the 'ctx' attribute for requests
        app.before(ctx -> ctx.attribute("ctx", ctx));
        return app;
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(getPort());
    }

}
