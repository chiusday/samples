<<<<<<< HEAD
package com.samples.vertx.reactive.visitor.model;
=======
package com.samples.common.visitor.model;
>>>>>>> branch 'master' of https://github.com/chiusday/samples.git

import org.springframework.http.ResponseEntity;

public abstract class BaseVisitorModelResp<T> extends BaseVisitorModel<T> {
	protected ResponseEntity<Object> responseEntity;

	public ResponseEntity<Object> getResponseEntity() {
		return this.responseEntity;
	}

	public void setResponseEntity(ResponseEntity<Object> responseEntity) {
		this.responseEntity = responseEntity;
	}
}
