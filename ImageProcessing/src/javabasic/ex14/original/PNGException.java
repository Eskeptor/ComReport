package javabasic.ex14.original;

import java.io.IOException;

public class PNGException extends IOException {
    public  PNGException() {}    
    public  PNGException( String what )  {
        super(what);
    }
}