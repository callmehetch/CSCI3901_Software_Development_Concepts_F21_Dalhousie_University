//package Assignment2;

public class MaximumRecursionDepth extends RuntimeException
{
    String str;
    int depth;
    public String getMessage()
    {
        return str;
    }

    public int getDepth()
    {
        return depth;
    }

    public MaximumRecursionDepth(String err_msg, int depth_traversed)
    {
        str=err_msg;
        depth=depth_traversed;
    }
}