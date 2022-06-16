#include <iostream>
#include <vector>
#include <cmath>
using namespace std;
void outAns(vector<vector<int> > m,int i,int j){
	if(i == j){
		cout<<"A"<<i;
	}else{
		cout<<"(";
		outAns(m,i,m[i][j]);
		outAns(m,m[i][j]+1,j);
		cout<<")";
	}
}
int main(int argc, char *argv[])
{
	int cnt;
	int n;
	vector<int > a;
	int j;
	int t;
	vector<vector<int> > dp(100,vector<int>(100));
	vector<vector<int> > m(100,vector<int>(100));
	cin>>cnt;
	for(int i=0;i<=cnt;i++){
		cin>>n;
		a.push_back(n);
	}
	for(int i =1;i<=cnt;i++)
		dp[i][i] =0;
	for(int l =2;l<=cnt;l++){
		for(int i=1;i<=cnt-l+1;i++){
			j=i+l-1;
			dp[i][j]=9999999;		
			for(int k=i;k<=j-1;k++){
				t = dp[i][k] + dp[k+1][j] + a[i-1]*a[k]*a[j];
				if(t < dp[i][j]){
					dp[i][j]=t;
					m[i][j]=k;
				}
			}
		}
	}
	cout<<dp[1][cnt]<<endl;
	outAns(m,1,cnt);
	return 0;
}