package test.connect.myapplication;

import static test.connect.myapplication.api.ApiClientFactory.GetPostApi;
import static test.connect.myapplication.api.ApiClientFactory.GetTrivaApi;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import test.connect.myapplication.api.SlimCallback;
import test.connect.myapplication.model.Post;
import test.connect.myapplication.model.Trivia;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView apiText1 = findViewById(R.id.activity_main_textView1);
        apiText1.setMovementMethod(new ScrollingMovementMethod());
        apiText1.setHeight(350);

        Button postByPathBtn = findViewById(R.id.activity_main_post_by_path_button);
        Button postByBodyBtn = findViewById(R.id.activity_main_post_by_body_button);
        EditText questionIn = findViewById(R.id.activity_main_question_editText);
        EditText answerIn = findViewById(R.id.activity_main_answer_editText);


        RegenerateAllTriviasOnScreen(apiText1);

        postByPathBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetTrivaApi().PostTriviaByPath(questionIn.getText().toString(), answerIn.getText().toString()).enqueue(new SlimCallback<Trivia>(trivia->{
                    RegenerateAllTriviasOnScreen(apiText1);
                    questionIn.setText("");
                    answerIn.setText("");
                }));
            }
        });

        postByBodyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trivia newTrivia = new Trivia();
                newTrivia.setQuestion(questionIn.getText().toString());
                newTrivia.setAnswer(answerIn.getText().toString());
                GetTrivaApi().PostTriviaByBody(newTrivia).enqueue(new SlimCallback<Trivia>(trivia->{
                    RegenerateAllTriviasOnScreen(apiText1);
                    questionIn.setText("");
                    answerIn.setText("");
                }));
            }
        });


    }

    void RegenerateAllTriviasOnScreen( TextView apiText1){

        GetTrivaApi().GetAllTrivia().enqueue(new SlimCallback<List<Trivia>>(trivias ->{
            apiText1.setText("");

            for (int i = trivias.size()-1; i>= 0; i--){
                apiText1.append(trivias.get(i).printable());
            }
        }, "GetAllTrivia"));

    }
}

