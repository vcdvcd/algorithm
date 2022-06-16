#include <iostream>
#include <string>
#include <cmath>
#include <iomanip>
using namespace std;
double fc(double x){
    double aa;double bb;double cc;
    aa=2*pow(x,2)*pow(sin(x),7);
    bb=3*pow(x,0.5)*cos(x);
    cc=exp(x)/5;
    return aa+bb-cc;
}

int main()
{   
    int i;
    long double x;
    long double l;long double r;
    //long double l=0;long double r=1;
    cin>>l>>r;
    
    if(fc(l)<=0&&fc(r)>=0){
        x=0;
        i=0;
        while(i<999){

            if(0-(fc(l))>=(fc(r)-0))x=r;
            else x=l;
            i++;
            if(fc((l+r)/2)<=0){l=(l+r)/2;continue;}
            if(fc((l+r)/2)>=0){r=(l+r)/2;continue;}
        }
        cout<<fixed<<setprecision(8)<<x<<endl;
    }
    else if(fc(l)>=0&&fc(r)<=0){
        x=0;
        i=0;
        while(i<999){

            if(0-(fc(l))>=(fc(r)-0))x=r;
            else x=l;
            i++;
            if(fc((l+r)/2)>=0){l=(l+r)/2;continue;}
            if(fc((l+r)/2)<=0){r=(l+r)/2;continue;}
        }
        cout<<fixed<<setprecision(8)<<x<<endl;
    }
    else cout<<"no"<<endl;
    
    return 0;
}
