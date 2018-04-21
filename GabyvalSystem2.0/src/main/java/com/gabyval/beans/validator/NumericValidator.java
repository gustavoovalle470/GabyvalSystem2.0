/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gabyval.beans.validator;

import java.util.Map;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.primefaces.validate.ClientValidator;
 
/**
 * Custom JSF Validator for Email input
 */
@FacesValidator("custom.OnlyNumericValidator")
public class NumericValidator implements Validator, ClientValidator {
 
    private Pattern pattern;
  
    private static final String EMAIL_PATTERN = "^[_0-9-\\+]";
  
    public NumericValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }
 
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(value == null) {
            return;
        }
         
        if(!pattern.matcher(value.toString()).matches()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Correo electronico invalido.", 
                        value + " is not a valid email;"));
        }
    }
 
    public Map<String, Object> getMetadata() {
        return null;
    }
 
    public String getValidatorId() {
        return "custom.emailValidator";
    }
     
}