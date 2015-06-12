# RippleEffect
The project is intended to provide the ripple effect animation in all android devices. 
All you need to Creaete RippleView instance and called its init method to acheive the ripple effect animation.

        yourview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RippleView rippleView = new RippleView(MainActivity.this);

                //finally call it to make ripples
                rippleView.initRipple(view, viewParent);
            }
        });


Here you can see the effect        
![Ripple Effect Demo](https://github.com/abhishekdabral/RippleEffect/blob/master/demo.gif "Ripple Effect")
