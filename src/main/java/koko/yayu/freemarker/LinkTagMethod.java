package koko.yayu.freemarker;

import java.util.List;

import freemarker.template.TemplateMethodModelEx;
import org.springframework.stereotype.Component;

@Component
public class LinkTagMethod implements TemplateMethodModelEx {

    @Override
    public Object exec(List arguments) {
        String path = arguments.get(0).toString();
        String appId = arguments.get(1).toString();
        return String.format(
            "<a href=\"/%s/%s\"><span class=\"tag is-medium\">%s</span></a>",
            path,
            appId,
            appId
        );
    }
}