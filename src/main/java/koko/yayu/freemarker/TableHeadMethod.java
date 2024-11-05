package koko.yayu.freemarker;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.stereotype.Component;

import org.apache.commons.lang3.StringUtils;

@Component
public class TableHeadMethod implements TemplateMethodModelEx {

    @Override
    public Object exec(List arguments) {
        return arguments.stream()
            .map(propertyName ->
                String.format("<th>%s %s %s</th>",
                    displayName(propertyName.toString()),
                    linkAsc(propertyName.toString()),
                    linkDesc(propertyName.toString()))
            )
            .collect(Collectors.joining("\n"));
    }

    private String displayName(String propertyName) {
        return Arrays.stream(StringUtils.splitByCharacterTypeCamelCase(propertyName))
            .map(StringUtils::capitalize)
            .collect(Collectors.joining(" "));
    }

    private String linkAsc(String propertyName) {
        return String.format("<a href=\"?order=%s-asc\"> &#8595; </a>", propertyName);
    }

    private String linkDesc(String propertyName) {
        return String.format("<a href=\"?order=%s-desc\"> &#8593; </a>", propertyName);
    }
}