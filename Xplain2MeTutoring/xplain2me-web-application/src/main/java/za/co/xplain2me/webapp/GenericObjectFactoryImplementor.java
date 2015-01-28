package za.co.xplain2me.webapp;

import javax.servlet.http.HttpServletRequest;

public abstract class GenericObjectFactoryImplementor implements ObjectFactory {
    
    // the HTTP servlet request
    protected HttpServletRequest request;
    
    /**
     * Default constructor
     * @param request 
     */
    protected GenericObjectFactoryImplementor(HttpServletRequest request) {
        this.request = request;
    }
    
    /**
     * Validates if the parameter name is provided and 
     * that the request is not null.
     * 
     * @param parameterName 
     */
    private void validate(String parameterName) {
        if (this.request == null) 
            throw new ObjectFactoryImplementorException("The http servlet request is null");
        if (parameterName == null || parameterName.isEmpty())
            throw new ObjectFactoryImplementorException("The parameter name is not provided.");
    }
    
    protected String getParameterValue(String parameterName) {
        validate(parameterName); 
        return request.getParameter(parameterName);
    }
    
    protected String[] getParameterValues(String parameterName) {
        validate(parameterName);
        return request.getParameterValues(parameterName);
    }
}
