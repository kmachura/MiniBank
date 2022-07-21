package pl.kmachuramika.minibank.enums;

public enum CurrencyNameEnum {

    PLN("polski złoty"),
    USD("dolar amerykański"),
    EUR("euro");

    private String name;

    CurrencyNameEnum(String s) {
        this.name = s;
    }

    public String getName() {
        return name;
    }
}
