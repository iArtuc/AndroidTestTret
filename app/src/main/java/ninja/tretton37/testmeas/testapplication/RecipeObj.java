package ninja.tretton37.testmeas.testapplication;

/**
 * Created by ilkinartuc on 25/01/2017.
 */

public class RecipeObj
{
    private int id;
    private String title;
    private String info;

    public RecipeObj(int id, String title, String info)
    {
        this.id = id;
        this.title = title;
        this.info = info;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getInfo()
    {
        return info;
    }

    public void setInfo(String info)
    {
        this.info = info;
    }
}
