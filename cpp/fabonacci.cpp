#include <iostream>
using namespace std;
int a[50];
int f(int n){
	if(a[n] != 0) return a[n];
	if(n == 1 || n == 2){
		a[n] = 1;
		return a[n];
	}
	a[n] = f(n - 1) + f(n - 2);
	return a[n];
}
int main(int argc, char *argv[])
{
	int n;
	cin>>n;
	cout<<f(n); 
	return 0;
}