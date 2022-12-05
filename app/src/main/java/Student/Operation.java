package Student;

import java.util.*;


public class operation {

    public operation() {

    }

    public CourseList findObj(String string, HashSet<CourseList> allCourses) {
        for (CourseList c: allCourses) {
            if (c.courseCode.equals(string)) {
                return c;
            }
        }
        return null;
    }

    // return 1 if courses have prerequisite, else return 0; modify prerequisite list
    public int hasPre(List<String> courses, List<String> prerequisites, HashSet<CourseList> allCourses) {
        int result = 0;
        for (String c: courses) {
            CourseList courseObj = findObj(c,allCourses);
            ArrayList<String> n = new ArrayList<String>();
            n.add(null);
            if (!courseObj.prerequisite.isEmpty()) {
                prerequisites.addAll(courseObj.prerequisite);
                result = 1;
            }
        }
        return result;
    }

    public ArrayList<ArrayList<String>> seperateByLevel(String aim, HashSet<CourseList> allCourses) {
        ArrayList<ArrayList<String>> listOfLists = new ArrayList<ArrayList<String>>();
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add(aim);
        listOfLists.add(list1);
        int i=0;
        ArrayList<String> pres = new ArrayList<String>();
        while (hasPre(listOfLists.get(i),pres,allCourses)==1) {
            ArrayList<String> preWithoutDuplicates = new ArrayList<>(new HashSet<>(pres));
            listOfLists.add(preWithoutDuplicates);
            i++;
            pres.clear();
        }
        return listOfLists;
    }

    public void addToList(ArrayList<String> order, ArrayList<Integer> level, ArrayList<String> addee, int levelnum,
                          HashSet<CourseList> pastcourses, HashSet<CourseList> allCourses) {
        for (String c: addee) {
            CourseList courseObj = findObj(c,allCourses);
            if (!order.contains(c) && !pastcourses.contains(courseObj)) {
                order.add(c);
                level.add(levelnum);
            }
        }
    }

    public ArrayList<Integer> generateNumList(int startInt, int totalNum) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i=0; i<totalNum; i++) {
            int remainder = (i+startInt) % 3;
            result.add(remainder);
        }
        return result;
    }

    public ArrayList<Integer> generateRep(String startSession,int num) {
        ArrayList<Integer> numlist = new ArrayList<Integer>();
        if (startSession.equals("Fall")) {
            numlist = generateNumList(0,num);
        }
        if (startSession.equals("Winter")) {
            numlist = generateNumList(1,num);
        }
        if (startSession.equals("Summer")) {
            numlist = generateNumList(2,num);
        }
        return numlist;
    }

    public ArrayList<String> generateSemList(String startSession, int num) {
        ArrayList<Integer> numlist = generateRep(startSession, num);

        ArrayList<String> result = new ArrayList<String>();
        for (int i=0; i<num; i++) {
            if (numlist.get(i)==0) {
                result.add("Fall");
            }
            if (numlist.get(i)==1) {
                result.add("Winter");
            }
            if (numlist.get(i)==2) {
                result.add("Summer");
            }
        }
        return result;
    }

    public ArrayList<Integer> helperYear(ArrayList<Integer> numlist) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        int index = numlist.indexOf(1);
        for (int i=0; i<index; i++) {
            result.add(0);
        }
        int count = 1;
        for (int j=index; j<numlist.size(); j=j+3) {
            if (j<numlist.size()) {
                result.add(count);
            }
            if ((j+1)<numlist.size()) {
                result.add(count);
            }
            if ((j+2)<numlist.size()) {
                result.add(count);
            }
            count++;
        }
        return result;
    }


    public int checkPre(CourseList courseObj, ArrayList<String> orderList,
                        ArrayList<ArrayList<String>> courseList) {
        int result =-1;
        for (String c: courseObj.prerequisite) {
            if (orderList.contains(c)) {

                for (int i=0; i<courseList.size(); i++) {
                    if (courseList.get(i).contains(c) && i>result) {
                        result = i;
                    }
                }
            }
        }
        return result;
    }


    public Timeline generateTimeline(HashSet<CourseList> pastcourses, HashSet<CourseList> futurecourses,
                                     HashSet<CourseList> allCourses, int startYear, String startSession) {

        ArrayList<String> order = new ArrayList<String>();
        ArrayList<Integer> level = new ArrayList<Integer>();

        List<ArrayList<ArrayList<String>>> bigList = new ArrayList<ArrayList<ArrayList<String>>>();

        int numOfAim = futurecourses.size();
        int max=0;
        for (CourseList aim: futurecourses) {
            bigList.add(seperateByLevel(aim.courseCode,allCourses));
            int levelnum = seperateByLevel(aim.courseCode,allCourses).size();
            if (levelnum>max) {
                max=levelnum;
            }
        }

        for (int i=0; i<max; i++) {
            for (int k=0; k<numOfAim; k++) {
                int sublen = bigList.get(k).size();
                int index = sublen-1-i;
                if (index>=0) {
                    addToList(order,level,bigList.get(k).get(index),i,pastcourses,allCourses);
                }
            }
        }

        ArrayList<String> timeList = generateSemList(startSession,order.size()*3);

        ArrayList<ArrayList<String>> courseList = new ArrayList<ArrayList<String>>();
        for (int i=0; i<timeList.size(); i++) {
            ArrayList<String>  tempt = new ArrayList<String>();
            courseList.add(tempt);
        }

        for (String c: order) {
            CourseList courseObj = findObj(c,allCourses);
            int lastPreIndex = checkPre(courseObj,order,courseList);
            int start = lastPreIndex+1;
            int a =0;
            for (int j=0; j<3; j++) {
                if (courseObj.offeringSession.contains(timeList.get(start+j))&&a==0) {
                    courseList.get(start+j).add(c);
                    a=1;
                }
            }
        }

        //clear empty sublist;
        int count=-1, random = 0;
        for (int m=courseList.size(); m>0; m--) {
            if (random == 0 && courseList.get(m-1).size()!=0) {
                count = m;
                random =1;
            }
        }

        //add year;
        ArrayList<Integer> numlist = generateRep(startSession,order.size()*3);
        ArrayList<Integer> addeeList = helperYear(numlist);
        for (int l=0; l<timeList.size(); l++) {
            int newYear = startYear + addeeList.get(l);
            String rep = Integer.toString(newYear) + " " + timeList.get(l);
            timeList.set(l, rep);
        }

        Timeline result = new Timeline(timeList.subList(0, count),courseList.subList(0, count));
        return result;

    }


}

