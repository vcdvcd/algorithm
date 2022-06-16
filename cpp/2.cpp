#include <iostream>
#include <cmath>
using namespace std;
int main(int argc, char *argv[])
{
	int x1;
	int x2;
	double sum=0;
	int n;
	int left;
	int right;
	bool f =true;
	cin>>x1>>x2;
	for(n=1;sum<x2;n++){
		sum+=sqrt((double)n)/(n+1);
		if(sum>x1 && f){
			left=n;
			f=false;
		}
	}
	right=n-2;
	cout<<left<<"<=n<="<<right;
	return 0;
}