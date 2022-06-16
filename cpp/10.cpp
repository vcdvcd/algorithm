#include <iostream>
#include <vector>
using namespace std;
vector<vector<int> > a(1000,vector<int>(2,0));
vector<int> b(26,-1);
int num;
int e;
void f(int i){
	for(int j =0;j<e;j++){
		if(a[j][0] == i && b[a[j][1]] == -1){
			cout<<(char)(a[j][1]+64);
			b[a[j][1]]=1;
			f(a[j][1]);
		}
	}
}
int main(int argc, char *argv[])
{
	int n;
	cin>>num>>e;
	for(int i =1;i<=num;i++){
		b.push_back(i);
	}
	for(int i =0;i<e;i++){
		for(int j = 0;j < 3;j++){
			cin>>n;
			if(j == 2) break;
			a[i][j] = n;
		}
	}
	cout<<"A";
	f(1);
	return 0;
}