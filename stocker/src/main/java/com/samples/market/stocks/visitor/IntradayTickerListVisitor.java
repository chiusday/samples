package com.samples.market.stocks.visitor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.samples.market.model.IntradayTicker;
import com.samples.market.stocks.visitor.interfaces.ConvertibleResponse;
import com.samples.market.stocks.visitor.interfaces.JsonQuoteConvertibleResponseVisitor;
import com.samples.market.stocks.visitor.model.IntradayTickerListVisitorModel;

@Service
public class IntradayTickerListVisitor 
		extends JsonQuoteConvertibleResponseVisitor<IntradayTicker> {

	@Override
	public void visit(IntradayTickerListVisitorModel visitorModel) {
		super.visit((ConvertibleResponse<IntradayTicker>)visitorModel);
		visitorModel.setResponseEntity
			(new ResponseEntity<>(visitorModel.getModel(), HttpStatus.OK));
	}
}
