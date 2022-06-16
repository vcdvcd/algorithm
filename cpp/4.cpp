#include <iostream>
#include <algorithm>
#include <vector>
#include <functional>
#include <numeric>
using namespace std;
bool cmp(int &a){
	return a%2;
}
int main(int argc, char *argv[])
{	
	vector<int> a;
	vector<int> b;
	int n;
	int f;
	int i;
	int x=0;
	int j=1;
	int cnt =0;
	b.push_back(0);
	while(cin>>n){
		if(n==0) break;
		cnt++;
		i=0;
		while(cin>>f){
			a.push_back(f);
			i++;
			if(i==n) break;	
		}
		vector<int>::iterator it =stable_partition(a.begin()+x,a.begin()+x+n,cmp);
		sort(a.begin()+x,it,greater<int>());
		sort(it,a.begin()+x+n);
		x+=n;
		b.push_back(x);
	}
	for(int x=0;x<cnt;x++){
		for(int m=b[x];m<b[x+1];m++){
			cout<<a[m]<<" ";
		}
		cout<<endl;
	}
	
	return 0;
}