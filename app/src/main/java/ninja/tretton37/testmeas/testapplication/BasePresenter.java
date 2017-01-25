package ninja.tretton37.testmeas.testapplication;

/**
 * Created by ilkinartuc on 25/01/2017.
 */

public interface BasePresenter<T extends BaseView>
{
    void start(T view);
}