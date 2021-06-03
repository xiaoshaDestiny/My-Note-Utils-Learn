package com.learn.boot.controller;

import com.learn.boot.common.ExpressionObject;
import com.learn.boot.config2.ParamCheck;
import com.learn.boot.dto.Baggage;
import com.learn.boot.dto.Tourist;
import org.springframework.stereotype.Service;

/**
 * @author xu.rb
 * @description: TODO
 * @since 2021-06-03 23:23
 */
@Service
public class CheckService {

    @ParamCheck(color = "${tourist.getBox().getColor()}", ownerName = "${tourist.getName()}")
    public String tourService(@ExpressionObject("tourist") Tourist tourist) {
        return tourist.toString();
    }


    //此处也可以用"${baggage.getBox().getColor()}"
    @ParamCheck(color = "${tourist.getPack().getBox().getColor()}", ownerName = "${tourist.getName()}")
    public String baggageService(@ExpressionObject("baggage") Baggage baggage, @ExpressionObject("tourist")Tourist tourist) {
        return baggage.toString() + tourist.toString();
    }
}
