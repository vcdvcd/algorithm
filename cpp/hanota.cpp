#include <iostream>
using namespace std;
void hanota(int n,char s,char m,char e){
	if(n == 1) printf("%c--%c\n",s,e);
	else{
		hanota(n - 1,s,e,m);
		printf("%c--%c\n",s,e);
		hanota(n - 1,m,s,e);
	}
}
int main(int argc, char *argv[])
{
	int n;
	cin>>n;
	hanota(n,'A','B','C');	
	return 0;
}