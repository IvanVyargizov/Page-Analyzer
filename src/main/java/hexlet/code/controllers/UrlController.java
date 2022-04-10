package hexlet.code.controllers;

import hexlet.code.domain.Url;
import hexlet.code.domain.query.QUrl;
import io.ebean.PagedList;
import io.javalin.http.Handler;
import io.javalin.http.NotFoundResponse;

import java.net.URL;
import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;

public class UrlController {
    public static Handler listUrls = ctx -> {
        int page = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
        int rowsPerPage = 10;
        int offset = (page - 1) * rowsPerPage;

        PagedList<Url> pagedUrls = new QUrl()
                .setFirstRow(offset)
                .setMaxRows(rowsPerPage)
                .orderBy()
                .id.asc()
                .findPagedList();

        List<Url> urls = pagedUrls.getList();

//        int lastPage = pagedUrls.getTotalPageCount() + 1;
//        int currentPage = pagedUrls.getPageIndex() + 1;
//        List<Integer> pages = IntStream
//                .range(1, lastPage)
//                .boxed()
//                .collect(Collectors.toList());


        ctx.attribute("urls", urls);
        ctx.attribute("page", page);
//        ctx.attribute("pages", pages);
//        ctx.attribute("currentPage", currentPage);
        ctx.render("urls/index.html");
    };

    public static Handler createUrl = ctx -> {
        String srtUrl = ctx.formParam("url");

        URL url;
        try {
            url = new URL(srtUrl);
        } catch (java.net.MalformedURLException exception) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flash-type", "danger");
            ctx.attribute("url", srtUrl);
            ctx.render("index.html");
            return;
        }
        String normalStrUrl = getUrl(url);
        Url urlSearch = new QUrl()
                .name.equalTo(normalStrUrl)
                .findOne();

        if (urlSearch != null) {
            ctx.sessionAttribute("flash", "Страница уже существует");
            ctx.sessionAttribute("flash-type", "danger");
            ctx.attribute("url", srtUrl);
            ctx.redirect("/");
            return;
        }
        Url newUrl = new Url(normalStrUrl);
        newUrl.save();

        ctx.sessionAttribute("flash", "Страница успешно добавлена");
        ctx.sessionAttribute("flash-type", "success");
        ctx.redirect("/urls");
    };

    public static Handler showUrl = ctx -> {
        int id = ctx.pathParamAsClass("id", Integer.class).getOrDefault(null);

        Url url = new QUrl()
                .id.equalTo(id)
                .findOne();

        if (url == null) {
            throw new NotFoundResponse();
        }
    };

    private static String getUrl(URL url) {
        return url.getPort() == -1 ? url.getProtocol() + "://" + url.getHost()
                : url.getProtocol() + "://" + url.getHost() + ":" + url.getPort();
    }

}
