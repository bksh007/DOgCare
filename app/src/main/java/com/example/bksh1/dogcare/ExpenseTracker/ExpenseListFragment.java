package com.example.bksh1.dogcare.ExpenseTracker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bksh1.dogcare.R;

import java.math.BigDecimal;
import java.util.Calendar;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class ExpenseListFragment extends Fragment {
    TextView emptyMessageView, summaryTitle, summaryToday, summaryWeek, summaryMonth;
    StickyListHeadersListView stickyListView;
    LinearLayout summaryView, summaryTodayBox, summaryWeekBox, summaryMonthBox;

    public ExpenseListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_expense_list, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.btn_Floating);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpenseActivity.activeExpenseId = -1;
                setExpenseItemFragment();
            }
        });

        emptyMessageView = (TextView) rootView.findViewById(R.id.tv_emptyMessage);
        stickyListView = (StickyListHeadersListView) rootView.findViewById(R.id.stickylistview_expenses);
        summaryTitle = (TextView) rootView.findViewById(R.id.tv_summaryTitle);
        summaryToday = (TextView) rootView.findViewById(R.id.tv_expense_summaryToday);
        summaryWeek = (TextView) rootView.findViewById(R.id.tv_expense_summaryWeek);
        summaryMonth = (TextView) rootView.findViewById(R.id.tv_expense_summaryMonth);
        summaryView = (LinearLayout) rootView.findViewById(R.id.lnl_expense_summaryView);
        summaryWeekBox = (LinearLayout) rootView.findViewById(R.id.lnl_expense_summaryWeekBox);
        summaryMonthBox = (LinearLayout) rootView.findViewById(R.id.lnl_expense_summaryMonthBox);

        populateExpenses();
        return rootView;
    }


    void populateExpenses() {
        SQLiteDatabase db = ExpenseDBHelper.getInstance(getContext()).getWritableDatabase();
        Cursor expCursor = db.rawQuery("SELECT  * FROM " + ExpenseContract.ExpenseEntry.TABLE_NAME +
                " ORDER BY " + ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_DATE +
                " DESC", null);

        if (expCursor.getCount() == 0) {
            emptyMessageView.setVisibility(View.VISIBLE);
            summaryView.setVisibility(View.GONE);
            stickyListView.setVisibility(View.GONE);
        } else {
            emptyMessageView.setVisibility(View.GONE);
            summaryView.setVisibility(View.VISIBLE);
            stickyListView.setVisibility(View.VISIBLE);
        }

        final ExpensesAdapter expensesAdapter = new ExpensesAdapter(getContext(), expCursor, 0);
        stickyListView.setAdapter(expensesAdapter);

        stickyListView.setClickable(true);
        stickyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {
                ExpenseActivity.activeExpenseId = (int) stickyListView.getAdapter().getItemId(position);
                setExpenseItemFragment();
            }
        });

        populateSummary(expCursor);
    }

    public void populateSummary(Cursor cursor) {
        BigDecimal[] totals = ExpensesAdapter.calculateExpenseSummary(cursor, Calendar.getInstance());

        BigDecimal totalDay = totals[0];
        BigDecimal totalWeek = totals[1];
        BigDecimal totalMonth = totals[2];

        String titleMsg = getResources().getString(R.string.expense_summary_view_title);
        summaryTitle.setText(titleMsg);
        summaryToday.setText("Rs." + totalDay.toString());
        summaryWeek.setText("Rs." + totalWeek.toString());
        summaryMonth.setText("Rs." + totalMonth.toString());

        if (totalDay.equals(totalWeek)) {
            summaryWeekBox.setVisibility(View.GONE);
        } else {
            summaryWeekBox.setVisibility(View.VISIBLE);
        }

        if (totalWeek.equals(totalMonth)) {
            summaryMonthBox.setVisibility(View.GONE);
        } else {
            summaryMonthBox.setVisibility(View.VISIBLE);
        }
    }

    void setExpenseItemFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.fragment_container, new ExpenseItemFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
