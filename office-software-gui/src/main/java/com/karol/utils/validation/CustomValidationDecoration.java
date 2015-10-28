package com.karol.utils.validation;

import org.controlsfx.control.decoration.Decoration;
import org.controlsfx.control.decoration.StyleClassDecoration;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;

import java.util.ArrayList;
import java.util.Collection;

public class CustomValidationDecoration extends GraphicValidationDecoration {

    @Override
    protected Collection<Decoration> createValidationDecorations(ValidationMessage message) {
        Collection<Decoration> decorations = super.createValidationDecorations(message);
        ArrayList<Decoration> myDecorations = new ArrayList<>(decorations);
        myDecorations.add(new StyleClassDecoration("error-text-field"));
        return myDecorations;
    }

}
