package ru.isemenov.springData.dto;

import lombok.Data;
import ru.isemenov.springData.entities.Product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

@Data
public class Cart {
    private List<OrderItemDto> items;
    private int totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addProduct(Product product){
        if (addProduct(product.getId())){
            return;
        }
        items.add(new OrderItemDto(product));
        recalculate();
    }

    public boolean addProduct(Long id){
        for (OrderItemDto o : items){
            if(o.getProductId().equals(id)){
                o.changeQuantity(1);
                recalculate();
                return true;
            }
        }
        return false;
    }

    public void decreaseProduct(Long id){
        Iterator<OrderItemDto> iter = items.iterator();
        while (iter.hasNext()){
            OrderItemDto o = iter.next();
            if(o.getProductId().equals(id)){
                o.changeQuantity(-1);
                if(o.getQuantity() <= 0){
                    iter.remove();
                }
                recalculate();
                return;
            }
        }
    }

    public void removeProduct(Long id){
        items.removeIf(new Predicate<OrderItemDto>() {
            @Override
            public boolean test(OrderItemDto o) {
                return o.getProductId().equals(id);
            }
        });
        recalculate();
    }


    public void clear(){
        items.clear();
        totalPrice = 0;
    }

    private void recalculate(){
        totalPrice = 0;
        for(OrderItemDto orderItemDto : items){
            totalPrice += orderItemDto.getPrice();
        }
    }
}