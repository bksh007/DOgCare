package com.example.bksh1.dogcare.ExpenseTracker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.bksh1.dogcare.R;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

public class ExpenseItemFragment extends Fragment implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener {

    public static Expense activeExpense;

    private TextView expenseDate, expenseTime;
    private EditText expenseAmount, expenseCategory, expenseDescription;
    private Button expenseAddButton, expenseDeleteButton, expenseCancelButton;
    private TagContainerLayout expenseCategoryTags;

    public ExpenseItemFragment() {
        // Required empty public constructor
    }

    public static void closeKeyboard(Context c, IBinder windowToken) {
        InputMethodManager mgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_expense_item, container, false);

        activeExpense = new Expense();
        if (ExpenseActivity.activeExpenseId != -1) {
            Expense actualExpense = ExpenseDBHelper.getInstance(getContext()).getExpenseById(ExpenseActivity.activeExpenseId);
            activeExpense.setId(actualExpense._id);
            activeExpense.setAmount(actualExpense.amount.toString());
            activeExpense.setDescription(actualExpense.description);
            activeExpense.setCategory(actualExpense.category);
            activeExpense.setDate(actualExpense.date.getTimeInMillis());
        } else {
            activeExpense.setDate(Calendar.getInstance().getTimeInMillis());
        }

        expenseDate = (TextView) rootView.findViewById(R.id.tv_expenseDate);
        expenseTime = (TextView) rootView.findViewById(R.id.tv_expenseTime);
        expenseAmount = (EditText) rootView.findViewById(R.id.et_expenseAmount);
        expenseCategory = (EditText) rootView.findViewById(R.id.expense_category);
//        expenseCategoryTags = (TagContainerLayout) rootView.findViewById(R.id.expense_categoryTags);
        expenseDescription = (EditText) rootView.findViewById(R.id.et_expenseDescription);
        expenseAddButton = (Button) rootView.findViewById(R.id.btn_add);
        expenseCancelButton = (Button) rootView.findViewById(R.id.btn_cancel);
        expenseDeleteButton = (Button) rootView.findViewById(R.id.btn_delete);

        initializeDateView();
        initializeTimeView();
        initializeAmountView();
//        initializeCategoryView();
        initializeDescriptionView();
        initializeCreateButtonView();
        initializeCancelButtonView();
        initializeDeleteButtonView();

        return rootView;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        activeExpense.date.set(Calendar.YEAR, year);
        activeExpense.date.set(Calendar.MONTH, monthOfYear);
        activeExpense.date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        expenseDate.setText(new SimpleDateFormat("dd MMM yyyy").format(activeExpense.date.getTime()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        activeExpense.date.set(Calendar.HOUR_OF_DAY, hourOfDay);
        activeExpense.date.set(Calendar.MINUTE, minute);
        expenseTime.setText(new SimpleDateFormat("hh:mm a").format(activeExpense.date.getTime()));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void initializeDateView() {
        expenseDate.setText(new SimpleDateFormat("dd MMM").format(activeExpense.date.getTime()));
        expenseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
    }

    public void showDatePickerDialog() {
        DatePickerFragment datePickerFrag = new DatePickerFragment();
        datePickerFrag.setTargetFragment(ExpenseItemFragment.this, 0);
        datePickerFrag.show(getFragmentManager(), "Datepicker");
    }

    public void initializeTimeView() {
        expenseTime.setText(new SimpleDateFormat("hh:mm a").format(activeExpense.date.getTime()));
        expenseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });
    }

    public void showTimePickerDialog() {
        TimePickerFragment timePickerFrag = new TimePickerFragment();
        timePickerFrag.setTargetFragment(ExpenseItemFragment.this, 0);
        timePickerFrag.show(getFragmentManager(), "Timepicker");
    }

    public void initializeAmountView() {
        if (activeExpense.amount != null) {
            expenseAmount.setText(activeExpense.amount.toString());
        }
    }

    public void initializeDescriptionView() {
        if (activeExpense.description != null) {
            expenseDescription.setText(activeExpense.description);
        }
    }

    public void initializeCategoryView() {
        ArrayList<String> categories = ExpenseDBHelper.getInstance(getContext()).getAllCategories();
        if (activeExpense.category != null) {
            expenseCategory.setText(activeExpense.category);
        }

        expenseCategoryTags.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                expenseCategory.setText(text);
                expenseCategory.setSelection(expenseCategory.getText().length());
            }

            @Override
            public void onTagLongClick(final int position, String text) {
            }

            @Override
            public void onSelectedTagDrag(int position, String text) {
            }

            @Override
            public void onTagCrossClick(int position) {
            }
        });

        expenseCategoryTags.setTags(categories);
    }

    public void initializeCreateButtonView() {
        if (ExpenseActivity.activeExpenseId != -1) {
            expenseAddButton.setText(R.string.update_expense_button);
        } else {
            expenseAddButton.setText(R.string.create_expense_button);
        }

        expenseAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOrUpdateExpense();
            }
        });
    }

    public void createOrUpdateExpense() {
        if (validateForm()) {
            activeExpense.setAmount(expenseAmount.getText().toString());
            activeExpense.setCategory(expenseCategory.getText().toString());
            activeExpense.setDescription(expenseDescription.getText().toString());

            if (ExpenseActivity.activeExpenseId != -1) {
                ExpenseDBHelper.getInstance(getContext()).updateExpense(activeExpense);
            } else {
                ExpenseDBHelper.getInstance(getContext()).addExpense(activeExpense);
            }
            setExpenseListFragment();
        }
    }

    public boolean validateForm() {
        try {
            BigDecimal amount = new BigDecimal(expenseAmount.getText().toString());
            if (amount.equals(BigDecimal.ZERO)) {
                expenseAmount.setError(getText(R.string.expense_form_amount_error));
                return false;
            }
            expenseAmount.setError(null);
            return true;
        } catch (Exception e) {
            expenseAmount.setError(getText(R.string.expense_form_amount_error));
            return false;
        }
    }

    public void initializeCancelButtonView() {
        expenseCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setExpenseListFragment();
            }
        });
    }

    void setExpenseListFragment() {
        closeKeyboard(getActivity(), expenseAmount.getWindowToken());
        ExpenseActivity.activeExpenseId = -1;
        activeExpense = null;

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.fragment_container, new ExpenseListFragment());
        transaction.commit();
    }

    public void initializeDeleteButtonView() {
        if (ExpenseActivity.activeExpenseId != -1) {
            expenseDeleteButton.setVisibility(View.VISIBLE);

            expenseDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setCancelable(true);
                    builder.setTitle(R.string.ask_expense_delete);
                    builder.setPositiveButton(R.string.yes_expense_delete, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ExpenseDBHelper.getInstance(getContext()).deleteExpenseById(ExpenseActivity.activeExpenseId);
                            setExpenseListFragment();
                        }
                    });
                    builder.create().show();
                }
            });
        } else {
            expenseDeleteButton.setVisibility(View.GONE);
        }

    }
}
