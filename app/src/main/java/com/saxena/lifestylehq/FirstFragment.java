package com.saxena.lifestylehq;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
//    private int max = 1070;
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

    public static void stopMusic(){
        BackgroundSoundService.stopMusic();
    }

    public static void startMusic(){
        BackgroundSoundService.startMusic();    }

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
                "The only way to predict the future is to create it.",
                "Your work is going to fill a large part of your life, and the only way to be truly satisfied is to do what you believe is great work. And the only way to do great work is to love what you do.",
                "Innovation distinguishes between a leader and a follower.",
                "Here's to the crazy ones. The misfits. The rebels. The troublemakers. The round pegs in the square holes. The ones who see things differently. They're not fond of rules. And they have no respect for the status quo. You can quote them, disagree with them, glorify or vilify them. About the only thing you can't do is ignore them. Because they change things. They push the human race forward. And while some may see them as the crazy ones, we see genius. Because the people who are crazy enough to think they can change the world, are the ones who do.",
                "If you really look closely, most overnight successes took a long time.",
                "It's not about money. It's about the people you have, how you're led, and how much you get it.",
                "The people who are crazy enough to think they can change the world are the ones who do.",
                "I'm convinced that about half of what separates the successful entrepreneurs from the non-successful ones is pure perseverance.",
                "Design is not just what it looks like and feels like. Design is how it works.",
                "Remembering that I'll be dead soon is the most important tool I've ever encountered to help me make the big choices in life.",
                "Have the courage to follow your heart and intuition. They somehow already know what you truly want to become.",
                "Be the change you wish to see in the world.",
                "Strength does not come from physical capacity. It comes from an indomitable will.",
                "The weak can never forgive. Forgiveness is the attribute of the strong.",
                "An eye for an eye will only make the whole world blind.",
                "Live as if you were to die tomorrow. Learn as if you were to live forever.",
                "Where there is love there is life.",
                "Happiness is when what you think, what you say, and what you do are in harmony.",
                "First they ignore you, then they laugh at you, then they fight you, then you win.",
                "You must be the change you wish to see in the world.",
                "The difference between what we do and what we are capable of doing would suffice to solve most of the world's problems.",
                "Action expresses priorities.",
                "A man is but the product of his thoughts. What he thinks, he becomes.",
                "The best way to find yourself is to lose yourself in the service of others.",
                "An ounce of practice is worth more than tons of preaching.",
                "Strength does not come from winning. Your struggles develop your strengths. When you go through hardships and decide not to surrender, that is strength.",
                "I will not let anyone walk through my mind with their dirty feet.",
                "An error does not become truth by reason of multiplied propagation, nor does truth become error because nobody sees it.",
                "The difference between what we do and what we are capable of doing would suffice to solve most of the world's problems.",
                "Speak only if it improves upon the silence.",
                "I offer you peace. I offer you love. I offer you friendship. I see your beauty. I hear your need. I feel your feelings.",
                "Be the change that you want to see in the world.",
                "In a gentle way, you can shake the world.",
                "Nobody can hurt me without my permission.",
                "Action is no less necessary than thought to the instinctive tendencies of the human frame.",
                "Prayer is not asking. It is a longing of the soul. It is daily admission of one's weakness. It is better in prayer to have a heart without words than words without a heart.",
                "Satisfaction lies in the effort, not in the attainment. Full effort is full victory.",
                "Earth provides enough to satisfy every man's needs, but not every man's greed.",
                "Service which is rendered without joy helps neither the servant nor the served.",
                "Even if you are a minority of one, the truth is the truth.",
                "The future depends on what you do today.",
                "You may never know what results come of your actions, but if you do nothing, there will be no results.",
                "Be congruent, be authentic, be your true self.",
                "Anger and intolerance are the twin enemies of correct understanding.",
                "The difference between what we do and what we are capable of doing would suffice to solve most of the world's problems.",
                "You don't know who is important to you until you actually lose them.",
                "I cannot conceive of a greater loss than the loss of one's self-respect.",
                "In doing something, do it with love or never do it at all.",
                "The only tyrant I accept in this world is the still voice within.",
                "Nobody can hurt me without my permission.",
                "An eye for an eye will make the whole world blind.",
                "True beauty lies in the purity of the heart.",
                "Each one has to find his peace from within. And peace to be real must be unaffected by outside circumstances.",
                "Your beliefs become your thoughts, your thoughts become your words, your words become your actions, your actions become your habits, your habits become your values, your values become your destiny."
        };
        int startId = 1001;

        Map<String, String> thoughts;

        for(int i=0;i< thoughtsArr.length;i++) {
            thoughts = new HashMap<>();
            thoughts.put("thought", thoughtsArr[i]);
            db.collection("thoughts").document(Integer.toString(startId++))
                    .set(thoughts);
//            max = startId;
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

        db.collection("thoughts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;
                            }
                            System.out.println("Number of thoughts "+count);
                            fetchThought(min +(count-1));

                        } else {
                            Log.d(getTag(), "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    private void fetchThought(int max){
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
                String thought = "*Thought for the day*\n\n_"+binding.textviewThoughtOfTheDay.getText().toString()+"_";
                System.out.println("Whatsapp Button Clicked");
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/html");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, thought+"\n\n*Get App*: https://play.google.com/store/apps/details?id=com.saxena.lifestylehq");

                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast myToast = Toast.makeText(getActivity(), "Whatsapp have not been installed.", Toast.LENGTH_SHORT);
                    myToast.show();
                    ex.printStackTrace();
                }
            }
        });

        binding.buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOneThoughtFromFirestore();
            }
        });

        Animation rotation = AnimationUtils.loadAnimation(getContext(),
                R.anim.rotate);
        binding.imageView2.startAnimation(rotation);

        Animation blink = AnimationUtils.loadAnimation(getContext(),
                R.anim.blink);
        binding.appHeading.startAnimation(blink);

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