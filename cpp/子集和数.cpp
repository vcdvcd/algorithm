#include <iostream>
using namespace std;
int b[1000];
int a[1000];
int res;
void f(int c,int n,int sum,int i,int r){
	if(i == n) return;
	if(r < c - sum) return;
	b[i] = 1;
	if(sum + a[i] == c){
		for(int j = 0;j <= i;j++){
			if(b[j] == 1){
				cout<<a[j]<<"  ";
			}
		}
		res++;
		cout<<endl;
	}else if(sum + a[i] + a[i + 1] <= c){
		f(c,n,sum + a[i],i + 1,r - a[i]);
	}
	if(sum + r - a[i] >= c && sum + a[i + 1] <= c){
		b[i]=0;
		f(c,n,sum,i+1,r - a[i]);
	}
	
}
int main(int argc, char *argv[])
{
	int n,c;
	int r = 0;	
	cin>>n>>c;
	for(int i = 0;i < n;i++){
		cin>>a[i];
		r += a[i];
	}
	sort(a,a + n);
	f(c,n,0,0,r);
	cout<<res;
	return 0;
}