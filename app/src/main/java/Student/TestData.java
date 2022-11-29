package Student;

import java.util.*;

public class TestData {
    public HashSet<CourseList> testCourse;
    public ArrayList<String> courseCodeList;

//所有course
    public TestData(){
        //course1;
        ArrayList<String> os1 = new ArrayList<String>();
        os1.add("F");
        os1.add("W");
        ArrayList<String> pre1 = new ArrayList<String>();
        pre1.add("CSCA08");
        pre1.add("CSCA67");
        CourseList c1 = new CourseList("CSCB07","c1",os1,pre1);

        //course2;
        ArrayList<String> os2 = new ArrayList<String>();
        os2.add("F");
        os1.add("W");
        ArrayList<String> pre2 = new ArrayList<String>();
        pre2.add("CSCA08");
        pre2.add("CSCA67");
        CourseList c2 = new CourseList("CSCA48","c2",os2,pre2);

        //course3
        ArrayList<String> os3 = new ArrayList<String>();
        os3.add("F");
        ArrayList<String> pre3 = new ArrayList<String>();
        pre3.add(null);
        CourseList c3 = new CourseList("CSCA20","c3",os3,pre3);

        //course4;
        ArrayList<String> os4 = new ArrayList<String>();
        os4.add("F");
        os4.add("W");
        os4.add("S");
        ArrayList<String> pre4 = new ArrayList<String>();
        pre4.add("CSCA08");
        pre4.add("CSCA67");
        pre4.add("MATB23");
        pre4.add("STAB52");
        CourseList c4 = new CourseList("CSCC55","c4",os4,pre4);

        //course5;
        ArrayList<String> os5 = new ArrayList<String>();
        os5.add("W");
        os5.add("S");
        ArrayList<String> pre5 = new ArrayList<String>();
        pre5.add("CSCA08");
        pre5.add("MATB23");
        pre5.add("STAB52");
        CourseList c5 = new CourseList("CSCB24","c5",os5,pre5);

        //course6;
        ArrayList<String> os6 = new ArrayList<String>();
        os6.add("S");
        ArrayList<String> pre6 = new ArrayList<String>();
        pre6.add("STAB52");
        CourseList c6 = new CourseList("CSCB35","c6",os6,pre6);

        //course7;
        ArrayList<String> os7 = new ArrayList<String>();
        os7.add("F");
        os7.add("W");
        os7.add("S");
        ArrayList<String> pre7 = new ArrayList<String>();
        pre7.add("CSCB07");
        pre7.add("STAB52");
        CourseList c7 = new CourseList("CSCB61","c7",os7,pre7);

        //course8;
        ArrayList<String> os8 = new ArrayList<String>();
        os8.add("F");
        os8.add("W");
        os8.add("S");
        ArrayList<String> pre8 = new ArrayList<String>();
        pre8.add("CSCB07");
        CourseList c8 = new CourseList("CSCB09","c8",os8,pre8);

        //course9;
        ArrayList<String> os9 = new ArrayList<String>();
        os9.add("F");
        os9.add("S");
        ArrayList<String> pre9 = new ArrayList<String>();
        pre9.add("CSCB07");
        pre9.add("CSCB09");
        CourseList c9 = new CourseList("CSCB89","c9",os9,pre9);

        //course10;
        ArrayList<String> os10 = new ArrayList<String>();
        os10.add("F");
        os10.add("W");
        ArrayList<String> pre10 = new ArrayList<String>();
        pre10.add("CSCC07");
        CourseList c10 = new CourseList("CSCC89","c10",os10,pre10);

        //course11;
        ArrayList<String> os11 = new ArrayList<String>();
        os11.add("F");
        os11.add("W");
        ArrayList<String> pre11 = new ArrayList<String>();
        pre11.add(null);
        CourseList c11 = new CourseList("CSCA33","c11",os11,pre11);

        //course12;
        ArrayList<String> os12 = new ArrayList<String>();
        os12.add("W");
        os12.add("S");
        ArrayList<String> pre12 = new ArrayList<String>();
        pre12.add("MATB23");
        pre12.add("STAB52");
        CourseList c12 = new CourseList("CSCA56","c12",os12,pre12);

        //course13;
        ArrayList<String> os13 = new ArrayList<String>();
        os13.add("W");
        ArrayList<String> pre13 = new ArrayList<String>();
        pre13.add("CSCC67");
        pre13.add("CSCC37");
        CourseList c13 = new CourseList("CSCD11","c13",os13,pre13);

        //course14;
        ArrayList<String> os14 = new ArrayList<String>();
        os14.add("W");
        os14.add("F");
        os14.add("S");
        ArrayList<String> pre14 = new ArrayList<String>();
        pre14.add("MATB23");
        pre14.add("STAB52");
        pre14.add("CSCC61");
        CourseList c14 = new CourseList("CSCD53","c14",os14,pre14);

        //course15;
        ArrayList<String> os15 = new ArrayList<String>();
        os15.add("W");
        ArrayList<String> pre15 = new ArrayList<String>();
        pre15.add("CSCD53");
        CourseList c15 = new CourseList("CSCD77","c15",os15,pre15);


        HashSet<CourseList> set = new HashSet<CourseList>();
        set.add(c1);
        set.add(c2);
        set.add(c3);
        set.add(c4);
        set.add(c5);
        set.add(c6);
        set.add(c7);
        set.add(c8);
        set.add(c9);
        set.add(c10);
        set.add(c11);
        set.add(c12);
        set.add(c13);
        set.add(c14);
        set.add(c15);

        testCourse = set;

        //初始化course code list
        //List<String> courseCodeList = new ArrayList<String>();
        courseCodeList = new ArrayList<String>();
        for (CourseList Course: testCourse) {
            courseCodeList.add(Course.courseCode);
        }
        //courseCodeList = CCL.toArray(new String[CCL.size()]);


    }

//现有 past course
    public TestData(int a){
        //course1;
        ArrayList<String> os1 = new ArrayList<String>();
        os1.add("F");
        os1.add("W");
        ArrayList<String> pre1 = new ArrayList<String>();
        pre1.add("CSCA08");
        pre1.add("CSCA67");
        CourseList c1 = new CourseList("CSCB07","c1",os1,pre1);

        //course2;
        ArrayList<String> os2 = new ArrayList<String>();
        os2.add("F");
        os1.add("W");
        ArrayList<String> pre2 = new ArrayList<String>();
        pre2.add("CSCA08");
        pre2.add("CSCA67");
        CourseList c2 = new CourseList("CSCA48","c2",os2,pre2);

        //course3
        ArrayList<String> os3 = new ArrayList<String>();
        os3.add("F");
        ArrayList<String> pre3 = new ArrayList<String>();
        pre3.add(null);
        CourseList c3 = new CourseList("CSCA20","c3",os3,pre3);




        HashSet<CourseList> set = new HashSet<CourseList>();
        set.add(c1);
        set.add(c2);
        set.add(c3);


        testCourse = set;

        //初始化course code list
        //List<String> courseCodeList = new ArrayList<String>();
        courseCodeList = new ArrayList<String>();
        for (CourseList Course: testCourse) {
            courseCodeList.add(Course.courseCode);
        }
        //courseCodeList = CCL.toArray(new String[CCL.size()]);


    }

}
