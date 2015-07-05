package com.project.utils;

import javax.faces.context.FacesContext;

/**
 * Утилитный класс для работы с контекстом
 * @author dmitriy
 */
public class ContextUtils {

    /**
     * Получить бин по его имени
     * @param beanName имя бина (Не название класса)
     * @return возвращает искомый бин
     */
    @SuppressWarnings("unchecked")
    public static <T> T findBean(String beanName) {
        FacesContext context = FacesContext.getCurrentInstance();
        return (T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
    }

}
