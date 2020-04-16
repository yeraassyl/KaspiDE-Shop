package com.company.items.clothes;

public class Jeans extends Clothes {

    private String style;
    private String cloth;

    public Jeans(String name, double price, int size, String color, String style, String cloth) {
        super(name, price, size, color);
        this.style = style;
        this.cloth = cloth;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getCloth() {
        return cloth;
    }

    public void setCloth(String cloth) {
        this.cloth = cloth;
    }

    @Override
    public String getDescription(){
        return String.format("%sStyle: %s\nCloth: %s\n", super.getDescription(), style, cloth);
    }
}
