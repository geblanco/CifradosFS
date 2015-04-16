package Cifrados;

/**
 * Created by guillermo on 15/04/15.
 */
public class CifradoGenerico
{
    String input;
    String output;

    public CifradoGenerico(String in, String out) {this.input = in; this.output = out;}

    public String getInput()
    {
        return input;
    }

    public void setInput(String input)
    {
        this.input = input;
    }

    public String getOutput()
    {
        return output;
    }

    public void setOutput(String output)
    {
        this.output = output;
    }

}
