package koko.yayu.freemarker;

import java.util.List;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.stereotype.Component;

@Component
public class AppIdMethod implements TemplateMethodModelEx {

    @Override
    public Object exec(List arguments) {
        String appId = arguments.get(0).toString();
        return String.format(
            "<a href=\"/app/%s\"><span class=\"tag is-medium\">%s</span></a>",
            appId,
            appId
        );
    }
}