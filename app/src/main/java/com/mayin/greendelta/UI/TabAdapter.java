package UI;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.mayin.greendelta.Employee_information;
import com.mayin.greendelta.Employee_skills;
import com.mayin.greendelta.Employeelist;


public class TabAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public TabAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
            Employee_information emp_info = new Employee_information();
            return emp_info;
            case 1:
                Employeelist emp_list= new  Employeelist();
                return emp_list;
            case 2:
                Employee_skills emp_skills = new Employee_skills();
                return emp_skills;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}