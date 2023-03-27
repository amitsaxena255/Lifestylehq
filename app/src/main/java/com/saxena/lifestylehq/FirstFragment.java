package com.saxena.lifestylehq;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.saxena.lifestylehq.databinding.FragmentFirstBinding;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FirstFragment extends Fragment {

    private static FragmentFirstBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private int min = 1001;
    private int max = 1070;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
//        hydrateThoughtsStore();
        getOneThoughtFromFirestore();

        binding = FragmentFirstBinding.inflate(inflater, container, false);

        TextView linkTextView = binding.privacyLink;

        // method to redirect to provided link
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());

        return binding.getRoot();

    }

    public static void toggleMusic(){
        binding.buttonMusic.callOnClick();
    }

    public int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    private void hydrateThoughtsStore(){
        String[] thoughtsArr = {"Believe in yourself and all that you are. Know that there is something inside you that is greater than any obstacle.",
                "You miss 100% of the shots you don't take.",
                "The only person you are destined to become is the person you decide to be.",
                "Don't watch the clock; do what it does. Keep going.",
                "If you look at what you have in life, you'll always have more. If you look at what you don't have in life, you'll never have enough.",
                "Success is not how high you have climbed, but how you make a positive difference to the world.",
                "Believe you can and you're halfway there.",
                "You are never too old to set another goal or to dream a new dream.",
                "The only way to do great work is to love what you do.",
                "Success is not final, failure is not fatal: it is the courage to continue that counts.",
                "It does not matter how slowly you go as long as you do not stop.",
                "If you can dream it, you can achieve it.",
                "Believe in the power of your dreams and the rest will follow.",
                "Everything you’ve ever wanted is on the other side of fear.",
                "You don’t have to be great to start, but you have to start to be great.",
                "I have not failed. I've just found 10,000 ways that won't work.",
                "Chase your dreams but always know the road that will lead you home again.",
                "Believe in the possibilities that life has to offer.",
                "Your only limit is the amount of action you take.",
                "Life is 10% what happens to us and 90% how we react to it.",
                "The journey of a thousand miles begins with one step.",
                "Don't be pushed around by the fears in your mind. Be led by the dreams in your heart.",
                "The only way to have a good day is to start it with a positive attitude.",
                "Be the reason someone smiles today.",
                "Success is not just about what you accomplish in your life, it’s about what you inspire others to do.",
                "Your greatest weakness is also your greatest strength.",
                "The difference between ordinary and extraordinary is that little extra.",
                "The best way to predict the future is to create it.",
                "Everything you want is on the other side of hard work.",
                "Believe you can and you're halfway there.",
                "You are never too old to set another goal or to dream a new dream.",
                "The only way to do great work is to love what you do.",
                "Success is not final, failure is not fatal: it is the courage to continue that counts.",
                "It does not matter how slowly you go as long as you do not stop.",
                "Believe in yourself and all that you are. Know that there is something inside you that is greater than any obstacle.",
                "You miss 100% of the shots you don't take.",
                "If you want to achieve greatness, stop asking for permission.",
                "The only person you are destined to become is the person you decide to be.",
                "Don't watch the clock; do what it does. Keep going.",
                "If you look at what you have in life, you'll always have more. If you look at what you don't have in life, you'll never have enough.",
                "Success is not how high you have climbed, but how you make a positive difference to the world.",
                "The biggest adventure you can ever take is to live the life of your dreams.",
                "Everything you’ve ever wanted is on the other side of fear.",
                "You don’t have to be great to start, but you have to start to be great.",
                "I have not failed. I've just found 10,000 ways that won't work.",
                "Believe in the power of your dreams and the rest will follow.",
                "Do not wait to strike till the iron is hot; but make it hot by striking.",
                "If you can dream it, you can achieve it.",
                "I can't change the direction of the wind, but I can adjust my sails to always reach my destination.",
                "Obstacles are those frightful things you see when you take your eyes off your goal.",
                "Believe in yourself, take on your challenges, dig deep within yourself to conquer fears. Never let anyone bring you down. You got this.",
                "Don't let yesterday take up too much of today.",
                "Life is 10% what happens to you and 90% how you react to it.",
                "The future belongs to those who believe in the beauty of their dreams.",
                "Believe in yourself, push your limits, experience life, conquer your goals, and be happy.",
                "The more you praise and celebrate your life, the more there is in life to celebrate.",
                "Do not go where the path may lead, go instead where there is no path and leave a trail.",
                "The only limit to our realization of tomorrow will be our doubts of today.",
                "The only way to have a good day is to decide to have a good day.",
                "What you get by achieving your goals is not as important as what you become by achieving your goals.",
                "The only time you should ever look back is to see how far you've come.",
                "If you don't design your own life plan, chances are you'll fall into someone else's plan. And guess what they have planned for you? Not much.",
        "The best way to predict the future is to create it.",
                "Chase your dreams until you catch them...and then dream, catch, and dream again!",
                "You are never too old to set another goal or to dream a new dream.",
                "Success is not the key to happiness. Happiness is the key to success. If you love what you are doing, you will be successful.",
                "Your time is limited, don't waste it living someone else's life. Don't be trapped by dogma – which is living with the results of other people's thinking.",
        "Life is a journey, and if you fall in love with the journey, you will be in love forever.",
                "The only way to do great work is to love what you do.",
                "The only way to predict the future is to create it."
        };
        int startId = 1001;

        Map<String, String> thoughts;

        for(int i=0;i< thoughtsArr.length;i++) {
            thoughts = new HashMap<>();
            thoughts.put("thought", thoughtsArr[i]);
            db.collection("thoughts").document(Integer.toString(startId++))
                    .set(thoughts);
            max = startId;
        }


    }
    private void getAllThoughtsFromFirestore(){
        db.collection("thoughts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(getTag(), document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(getTag(), "Error getting documents.", task.getException());
                        }
                    }
                });

    }

    private void getOneThoughtFromFirestore(){
        System.out.println("Getting a new thought ... ");
        String thoughtId = Integer.toString(getRandomNumberUsingNextInt(min,max));
        DocumentReference docRef = db.collection("thoughts").document(thoughtId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot document) {
                Log.d(getTag(), document.getId() + " => " + document.getData());
                binding.textviewThoughtOfTheDay.setText(document.getData().get("thought").toString());
            }
        });
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        binding.buttonMusic.callOnClick();
        binding.buttonWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String thought = binding.textviewThoughtOfTheDay.getText().toString();
                System.out.println("Whatsapp Button Clicked");
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, thought);
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast myToast = Toast.makeText(getActivity(), "Whatsapp have not been installed.", Toast.LENGTH_SHORT);
                    myToast.show();

                }
            }
        });

        binding.buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOneThoughtFromFirestore();
            }
        });

        binding.buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String thought = binding.textviewThoughtOfTheDay.getText().toString();
                System.out.println("Whatsapp Button Clicked");

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, thought);

                try {
                    startActivity(Intent.createChooser(share, "Share with"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast myToast = Toast.makeText(getActivity(), "Oops, something went wrong.", Toast.LENGTH_SHORT);
                    myToast.show();
                }
            }
        });


//        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}