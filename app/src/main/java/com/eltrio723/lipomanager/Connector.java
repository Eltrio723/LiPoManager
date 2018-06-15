package com.eltrio723.lipomanager;

public enum Connector {
    XT60 ("XT60"),
    XT30 ("XT30"),
    JST ("JST"),
    MOLEX ("Molex"),
    MOLEX_PICOBLADE_2 ("Picoblade"),
    JST_PH_2 ("JST PH"),
    DEANS ("Deans"),
    EC2 ("EC2"),
    EC3 ("EC3"),
    EC5 ("EC5"),
    TRX ("TRX"),
    HXT ("HXT"),
    TAMIYA ("Tamiya"),
    OTHER ("Other");

    private final String name;

    private Connector(String s){
        name = s;
    }

    public boolean equalsName(String otherName){
        return name.equals(otherName);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
