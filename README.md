# EEAnimator
A sikeeo's Android animation library
=======

[![API](https://img.shields.io/badge/API-14%2B-green.svg)](https://github.com/sikeeo/EEAnimator)


#Usage

```java
EEViewAnimator.animate(ivSample1, ivSample2, tvTest)
                  .translationY(-1000, 0)
                  .alpha(0, 1f)
                  .bounce()
                  .duration(3000)
                .nextAnimate(ivSample2)
                  .translationX(0, -500, 0)
                  .overShoot()
                  .duration(2200)
                .nextAnimate(tvTest)
                  .rotation(360)
                  .duration(2000)
                .nextAnimate(ivSample2)
                  .scale(1f, 0.5f, 1f)
                  .accelerate()
                  .duration(1500)
                .start();
       
```

[![gif](http://i.giphy.com/BHM3pgcKJIhcA.gif)](https://youtu.be/SHS1neM53Hc)

If you do not use a EEAnimator

```java
AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(ivSample1, "translationY", -1000, 0),
                ObjectAnimator.ofFloat(ivSample2, "translationY", -1000, 0),
                ObjectAnimator.ofFloat(tvTest, "translationY", -1000, 0),
        );
        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.setDuration(3000);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(
                        ObjectAnimator.ofFloat(ivSample2, "translationX", 0, -500, 0),
                        );
                animatorSet2.setInterpolator(new OvershootInterpolator());
                animatorSet2.setDuration(2200);
                animatorSet2.start();
                animatorSet2.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        AnimatorSet animatorSet3 = new AnimatorSet();
                        animatorSet3.playTogether(
                                ObjectAnimator.ofFloat(tvTest, "rotation",360)
                                );
                        animatorSet3.setDuration(2200);
                        animatorSet3.start();
                        animatorSet3.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                AnimatorSet animatorSet4 = new AnimatorSet();
                                animatorSet4.playTogether(
                                        ObjectAnimator.ofFloat(ivSample2, "scale",1f, 0.5f, 1f)
                                );
                                animatorSet4.setDuration(1500);
                                animatorSet4.setInterpolator(new AccelerateInterpolator());
                                animatorSet4.start();
                            }
                        });
                    }
                });
            }
        });

        animatorSet.start();
```

#More

Add listeners
```java
EEViewAnimator.animate(ivSample1, ivSample2, tvTest)
                .translationY(-1000, 0)
                .alpha(0, 1f)
                .bounce()
                .duration(3000)
                .nextAnimate(tvTest)
                .scale(1f, 0.5f, 1f)
                .onStop(new EEAnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        tvTest.setText("Bye World");
                    }
                })
                .duration(3000)
                .start();
```

```java
EEViewAnimator
       .animate(image)
       .onStart(new EEAnimationListener.Start() {
                    @Override
                    public void onStart() {
                        
                    }
                })
        .onStop(new EEAnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        
                    }
                })
       .start();
```


View Height / Width (dp)
```java
EEViewAnimator
       .animate(view)
           .width(100,200)
           .height(50,100)
       .start();
```

Color
```java
EEViewAnimator
       .animate(view)
            .textColor(Color.BLACK,Color.GREEN)
            .backgroundColor(Color.WHITE,Color.BLACK)
       .start();
```


#Download

Add into your **build.gradle**

```groovy
compile 'com.sikeeo.eeanimator:eeanimator:1.0.0@aar'
```

#Community

sikeeo@gmail.com

#License

  Copyright 2016 sikeeo

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 
