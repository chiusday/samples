package com.samples.market.stocks.visitor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.samples.market.model.HistoricalTickerList;
import com.samples.market.stocks.converter.interfaces.IConverter;
import com.samples.market.stocks.visitor.interfaces.ConvertibleResponse;
import com.samples.market.stocks.visitor.interfaces.ConvertibleResponseVisitor;
import com.samples.market.stocks.visitor.model.HistoricalTickerListVisitorModel;

@Service
public class HistoricalTickerListVisitor 
		extends ConvertibleResponseVisitor<HistoricalTickerList> {
	
	@Override
	public void visit(HistoricalTickerListVisitorModel visitorModel) {
		super.visit(visitorModel);
		visitorModel.setResponseEntity
			(new ResponseEntity<Object>(visitorModel.getModel(), HttpStatus.OK));
	}
}
