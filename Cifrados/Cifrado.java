package Cifrados;

import Util.ParamsException;

/**
 * Created by guillermo on 15/04/15.
 */
public interface Cifrado
{
    public void cifrar() throws ParamsException;
    public void descifrar() throws ParamsException;
    public void set(Object[] args);
    public Object[] get();
}
