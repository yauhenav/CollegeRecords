package com.yauhenav.web;

import java.io.*;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yauhenav.logic.service.*;
import com.yauhenav.logic.dto.*;
import com.yauhenav.logic.exception.*;

public class CollegeRecords extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        try {
            HttpSession sessObj = req.getSession(true);
            Service sesMngObj = (Service) sessObj.getAttribute("sessionObject");
            resp.setContentType("text/html;charset=utf-8");
            pw = resp.getWriter();
            String action = req.getParameter("action");

            switch (action) {
                case "Show all students":
                    executeShowAllStudents(resp, pw, sesMngObj, sessObj, req);
                    break;
                case "Show student":
                    executeShowOneStudent(resp, sesMngObj, req, pw);
                    break;
                case "Add new student":
                    executeAddNewStudent(resp, pw, sesMngObj, req);
                    break;
                case "Update student":
                    executeUpdateStudent(resp, pw, sesMngObj, req);
                    break;
                case "Delete student":
                    executeDeleteStudent(resp, pw, sesMngObj, req);
                    break;
                case "Show subject":
                    executeShowOneSubject(resp, pw, sesMngObj, req);
                    break;
                case "Show all subjects":
                    executeShowAllSubjects(resp, pw, sesMngObj, req);
                    break;
                case "Add new subject":
                    executeAddNewSubject(resp, pw, sesMngObj, req);
                    break;
                case "Update subject":
                    executeUpdateExistingSubject(resp, pw, sesMngObj, req);
                    break;
                case "Delete subject":
                    executeDeleteSpecifiedSubject(resp, pw, sesMngObj, req);
                    break;
                case "Show mark":
                    executeShowOneSpecifiedMark(resp, pw, sesMngObj, req);
                    break;
                case "Show all marks":
                    executeShowAllMarks(resp, pw, sesMngObj, req);
                    break;
                case "Show student's marks":
                    executeShowMarksOneStudent(resp, pw, sesMngObj, req);
                    break;
                case "Add new mark":
                    executeAddNewMark(resp, pw, sesMngObj, req);
                    break;
                case "Update existing mark":
                    executeUpdateExistingMark(resp, pw, sesMngObj, req);
                    break;
                case "Delete mark":
                    executeDeleteSpecifiedMark(resp, pw, sesMngObj, req);
                    break;
                case "Close everything":
                    executeCloseEverything(pw, sessObj);
                    break;
                default:
                    pw.println("Your request doesn't match any available action");
            }
        } catch (DaoException | ServiceException exc) {
            exc.printStackTrace();
            pw.println("An error occurred while trying to process your request");
        }
    }

    /**
     * Display all students
     */
    private void executeShowAllStudents(HttpServletResponse resp, PrintWriter pw, Service sesMngObj, HttpSession sessObj, HttpServletRequest req) throws DaoException, ServiceException, IOException, ServletException {
        pw.println("<B>Here's a list of all students in the DB</B>");
        pw.println("<table border=1>");
        //Uncomment the line below to print session number to web-page for test purposes
        pw.println(sessObj);
        //Uncomment the line below to print session number to web server console for test purposes
        System.out.println(sessObj);
        List<Student> lst = sesMngObj.displayAllStudents();
        req.setAttribute("list", lst);
        req.setAttribute("message", "Here goes the list of all students in the DB");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/single-result.jsp");
        dispatcher.forward (req, resp);
    }

    /**
     * Display one specified student
     */
    private void executeShowOneStudent (HttpServletResponse resp, Service sesMngObj, HttpServletRequest req, PrintWriter pw) throws DaoException, ServiceException, IOException, ServletException {
        String paramVal = req.getParameter("show_student_ID");
        if (paramVal.matches("[0-9]+")) {
            int key = Integer.parseInt(paramVal);
            Student student = new Student(key);
            student = sesMngObj.displayOneStudent(student);
            req.setAttribute("item", student);
            req.setAttribute("message", "Here goes one selected student");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/single-result.jsp");
            dispatcher.forward (req, resp);
        } else {
            pw.println("<font color=\"red\">You've entered invalid ID value, " +
                    "go back and enter a valid ID</font>");
        }
    }

    /**
     * Add new student
     */
    private void executeAddNewStudent (HttpServletResponse resp, PrintWriter pw, Service sesMngObj, HttpServletRequest req) throws DaoException, ServiceException, IOException, ServletException {
        String idValue = req.getParameter("new_student_ID");
        String nameValue = req.getParameter("new_student_name");
        String surnameValue = req.getParameter("new_student_surname");
        if (idValue.matches("[0-9]+") && nameValue.matches("[A-Za-z]+")
                && surnameValue.matches("[A-Za-z]+")) {
            int id = Integer.parseInt(idValue);
            Student student = new Student(id, nameValue, surnameValue);
            sesMngObj.addStudent(student);
            req.setAttribute("item", student);
            req.setAttribute("message", "This new student has been added to the DB");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/single-result.jsp");
            dispatcher.forward (req, resp);
        } else {
            pw.println("<font color=\"red\">You've entered incorrect name or surname, " +
                    "go back and enter a valid name</font>");
        }
    }

    /**
     * Update existing student
     */
    private void executeUpdateStudent (HttpServletResponse resp, PrintWriter pw, Service sesMngObj, HttpServletRequest req) throws DaoException, ServiceException, IOException, ServletException {
        String idValue = req.getParameter("update_student_ID");
        String nameValue = req.getParameter("update_student_name");
        String surnameValue = req.getParameter("update_student_surname");
        if (idValue.matches("[0-9]+") && nameValue.matches("[A-Za-z]+")
                && surnameValue.matches("[A-Za-z]+")) {
            int id = Integer.parseInt(idValue);
            Student student = new Student(id, nameValue, surnameValue);
            sesMngObj.updateStudent(student);
            req.setAttribute("item", student);
            req.setAttribute("message", "This is the updated student");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/single-result.jsp");
            dispatcher.forward (req, resp);
        } else {
            pw.println("<font color=\"red\">You've entered incorrect name or surname, " +
                    "go back and enter a valid name</font>");
        }
    }

    /**
     * Delete specified student
     */
    private void executeDeleteStudent (HttpServletResponse resp, PrintWriter pw, Service sesMngObj, HttpServletRequest req) throws DaoException, ServiceException, IOException, ServletException {
        String idValue = req.getParameter("delete_student_ID");
        if (idValue.matches("[0-9]+")) {
            int id = Integer.parseInt(idValue);
            Student student = new Student (id);
            sesMngObj.deleteStudent(student);
            req.setAttribute("item", student);
            req.setAttribute("message", "This student has been removed from the DB");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/single-result.jsp");
            dispatcher.forward (req, resp);
        } else {
            pw.println("<font color=\"red\">You've entered invalid ID value, " +
                    "go back and enter a valid ID</font>");
        }
    }

    /**
     * Display one specified subject
     */
    private void executeShowOneSubject (HttpServletResponse resp, PrintWriter pw, Service sesMngObj, HttpServletRequest req) throws DaoException, ServiceException, IOException, ServletException {
        String paramVal = req.getParameter("show_subject_ID");
        if (paramVal.matches("[0-9]+")) {
            int key = Integer.parseInt(paramVal);
            Subject subject = new Subject(key);
            subject = sesMngObj.displayOneSubject(subject);
            req.setAttribute("item", subject);
            req.setAttribute("message", "Here goes one selected subject");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/single-result.jsp");
            dispatcher.forward (req, resp);
        } else {
            pw.println("<font color=\"red\">You've entered invalid ID value, " +
                    "go back and enter a valid ID</font>");
        }
    }

    /**
     * Display all subjects
     */
    private void executeShowAllSubjects (HttpServletResponse resp, PrintWriter pw, Service sesMngObj, HttpServletRequest req) throws DaoException, ServiceException, IOException, ServletException {
        pw.println("<B>Here's a list of all subjects in the DB</B>");
        pw.println("<table border=1>");
        List<Subject> lst = sesMngObj.displayAllSubjects();
        req.setAttribute("list", lst);
        req.setAttribute("message", "Here goes the list of all subjects in the DB");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/single-result.jsp");
        dispatcher.forward (req, resp);
    }

    /**
     * Add new subject
     */
    private void executeAddNewSubject (HttpServletResponse resp, PrintWriter pw, Service sesMngObj, HttpServletRequest req) throws DaoException, ServiceException, IOException, ServletException {
        String idValue = req.getParameter("new_subject_ID");
        String descriptionValue = req.getParameter("new_subject_description");
        if (idValue.matches("[0-9]+") && descriptionValue.matches("[A-Za-z]+")) {
            int id = Integer.parseInt(idValue);
            Subject subject = new Subject(id, descriptionValue);
            sesMngObj.addSubject(subject);
            req.setAttribute("item", subject);
            req.setAttribute("message", "This new subject has been added to the DB");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/single-result.jsp");
            dispatcher.forward (req, resp);
        } else {
            pw.println("<font color=\"red\">You've entered incorrect description, " +
                    "go back and enter a valid description</font>");
        }
    }

    /**
     * Update existing subject
     */
    private void executeUpdateExistingSubject (HttpServletResponse resp, PrintWriter pw, Service sesMngObj, HttpServletRequest req) throws DaoException, ServiceException, IOException, ServletException {
        String idValue = req.getParameter("update_subject_ID");
        String descriptionValue = req.getParameter("update_subject_description");
        if (idValue.matches("[0-9]+") && descriptionValue.matches("[A-Za-z]+")) {
            int id = Integer.parseInt(idValue);
            Subject subject = new Subject(id, descriptionValue);
            sesMngObj.updateSubject(subject);
            req.setAttribute("item", subject);
            req.setAttribute("message", "This is the updated subject");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/single-result.jsp");
            dispatcher.forward (req, resp);
        } else {
            pw.println("<font color=\"red\">You've entered incorrect description, " +
                    "go back and enter a valid one</font>");
        }
    }

    /**
     * Delete specified subject
     */
    private void executeDeleteSpecifiedSubject (HttpServletResponse resp, PrintWriter pw, Service sesMngObj, HttpServletRequest req) throws DaoException, ServiceException, IOException, ServletException {
        String idValue = req.getParameter("delete_subject_ID");
        if (idValue.matches("[0-9]+")) {
            int id = Integer.parseInt(idValue);
            Subject subject = new Subject(id);
            sesMngObj.deleteSubject(subject);
            req.setAttribute("item", subject);
            req.setAttribute("message", "This subject has been removed from the DB");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/single-result.jsp");
            dispatcher.forward (req, resp);
        } else {
            pw.println("<font color=\"red\">You've entered invalid ID value, " +
                    "go back and enter a valid ID</font>");
        }
    }

    /**
     * Show one specified mark
     */
    private void executeShowOneSpecifiedMark (HttpServletResponse resp, PrintWriter pw, Service sesMngObj, HttpServletRequest req) throws DaoException, ServiceException, IOException, ServletException {
        String paramVal = req.getParameter("show_mark_ID");
        if (paramVal.matches("[0-9]+")) {
            int id = Integer.parseInt(paramVal);
            Mark mark = new Mark (id);
            mark = sesMngObj.displayOneMark(mark);
            req.setAttribute("item", mark);
            req.setAttribute("message", "Here goes one selected mark");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/single-result.jsp");
            dispatcher.forward (req, resp);
        } else {
            pw.println("<font color=\"red\">You've entered invalid ID value, " +
                    "go back and enter a valid ID</font>");
        }
    }

    /**
     * Show all marks
     */
    private void executeShowAllMarks (HttpServletResponse resp, PrintWriter pw, Service sesMngObj, HttpServletRequest req) throws DaoException, ServiceException, IOException, ServletException {
        pw.println("<B>Here's a list of all marks in the DB</B>");
        pw.println("<table border=1>");
        List<Mark> lst = sesMngObj.displayAllMarks();
        req.setAttribute("list", lst);
        req.setAttribute("message", "Here goes the list of all marks in the DB");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/single-result.jsp");
        dispatcher.forward (req, resp);
    }

    /**
     * Display all marks of one student
     */
    private void executeShowMarksOneStudent (HttpServletResponse resp, PrintWriter pw, Service sesMngObj, HttpServletRequest req) throws DaoException, ServiceException, IOException, ServletException {
        String paramVal = req.getParameter("show_all_marks_1stud");
        if (paramVal.matches("[0-9]+")) {
            int studentId = Integer.parseInt(paramVal);
            Mark mark = new Mark(0, 0, studentId, 0);
            List<Mark> lst = sesMngObj.displayMarksOneStud(mark);
            req.setAttribute("list", lst);
            req.setAttribute("message", "Here goes the list of all marks of one selected student");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/single-result.jsp");
            dispatcher.forward (req, resp);
        } else {
            pw.println("<font color=\"red\">You've entered invalid ID value, " +
                    "go back and enter a valid ID</font>");
        }
    }

    /**
     * Add new mark
     */
    private void executeAddNewMark (HttpServletResponse resp, PrintWriter pw, Service sesMngObj, HttpServletRequest req) throws DaoException, ServiceException, IOException, ServletException {
        String idValue = req.getParameter("new_mark_ID");
        String valueValue = req.getParameter("new_mark_value");
        String studentIdValue = req.getParameter("new_mark_studentId");
        String subjectIdValue = req.getParameter("new_mark_subjectId");
        if (idValue.matches("[0-9]+") && valueValue.matches("[0-9]+")
                && studentIdValue.matches("[0-9]+")
                && subjectIdValue.matches("[0-9]+")) {
            int id = Integer.parseInt(idValue);
            int value = Integer.parseInt(valueValue);
            int studentId = Integer.parseInt(studentIdValue);
            int subjectId = Integer.parseInt(subjectIdValue);
            Mark mark = new Mark (id, value, studentId, subjectId);
            sesMngObj.addMark (mark);
            req.setAttribute("item", mark);
            req.setAttribute("message", "This new mark has been added to the DB");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/single-result.jsp");
            dispatcher.forward (req, resp);
        } else {
            pw.println("<font color=\"red\">You've entered incorrect mark id " +
                    "or mark value, or student id, or subject id, go back " +
                    "and enter a valid values</font>");
        }
    }

    /**
     * Update existing mark
     */
    private void executeUpdateExistingMark (HttpServletResponse resp, PrintWriter pw, Service sesMngObj, HttpServletRequest req) throws DaoException, ServiceException, IOException, ServletException {
        String idValue = req.getParameter("update_mark_ID");
        String valueValue = req.getParameter("update_mark_value");
        String studentIdValue = req.getParameter("update_mark_studentId");
        String subjectIdValue = req.getParameter("update_mark_subjectId");
        if (idValue.matches("[0-9]+") && valueValue.matches("[0-9]+")
                && studentIdValue.matches("[0-9]+")
                && subjectIdValue.matches("[0-9]+")) {
            int id = Integer.parseInt(idValue);
            int value = Integer.parseInt(valueValue);
            int studentId = Integer.parseInt(studentIdValue);
            int subjectId = Integer.parseInt(subjectIdValue);
            Mark mark = new Mark(id, value, studentId, subjectId);
            sesMngObj.updateMark (mark);
            req.setAttribute("item", mark);
            req.setAttribute("message", "This is the updated subject");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/single-result.jsp");
            dispatcher.forward (req, resp);
        } else {
            pw.println("<font color=\"red\">You've entered incorrect mark id " +
                    "or mark value, or student id, or subject id, " +
                    "go back and enter a valid values</font>");
        }
    }

    /**
     * Delete specified mark
     */
    private void executeDeleteSpecifiedMark (HttpServletResponse resp, PrintWriter pw, Service sesMngObj, HttpServletRequest req) throws DaoException, ServiceException, IOException, ServletException {
        String idValue = req.getParameter("delete_mark_ID");
        if (idValue.matches("[0-9]+")) {
            int id = Integer.parseInt(idValue);
            Mark mark = new Mark (id);
            sesMngObj.deleteMark(mark);
            req.setAttribute("item", mark);
            req.setAttribute("message", "This mark has been removed from the DB");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/single-result.jsp");
            dispatcher.forward (req, resp);
        } else {
            pw.println("<font color=\"red\">You've entered invalid ID value, " +
                    "go back and enter a valid ID</font>");
        }
    }

    /**
     * Close the session; all 'PreparedStatement' objects and the connection will be closed by MySesListener
     */
    private void executeCloseEverything (PrintWriter pw, HttpSession sessObj) throws DaoException {
        pw.println("<B>This session has been closed, resulting in closure of all prepared statements, and connection</B>");
        pw.print("<a href=\"index.jsp\">Go to Home Page</a>");
        sessObj.invalidate();
    }
}
