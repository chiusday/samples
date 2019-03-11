<<<<<<< HEAD
package com.samples.vertx.reactive.visitor.model;

import com.samples.vertx.reactive.visitor.interfaces.IVisitorModel;

public abstract class BaseVisitorModel<T> implements IVisitorModel<T> {
=======
package com.samples.common.visitor.model;

public abstract class BaseVisitorModel<T> {
>>>>>>> branch 'master' of https://github.com/chiusday/samples.git
//	protected Type type;
	protected T model;
	protected boolean hasError;
	protected String message;
	
	public T getModel() {
		return model;
	}
	public void setModel(T model) {
		this.model = model;
	}
	public boolean isHasError() {
		return hasError;
	}
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
	
}
