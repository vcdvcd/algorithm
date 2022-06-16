#include <iostream>
#define N 7
 
using namespace std;
 
void matrixChainOrder(int p[],int length,int m[][N],int s[][N])
{
    int i,l,j,k;
    int q;
    for(i=0;i<length;++i)
        m[i][i]=0;//��¼������������˷�����Сֵ
    for(l=2;l<length;++l)//lΪ�������ĳ���
    {
        for(i=1;i<length-l+1;++i)
        {
            j=i+l-1;
            m[i][j]=100000;
            for(k=i;k<=j-1;++k)
            {
               q=m[i][k]+m[k+1][j]+p[i-1]*p[k]*p[j];
               if(q<m[i][j])
               {
                   m[i][j]=q;
                   s[i][j]=k;//s[][]��¼�������Ž�������Ϣ
               }
            }
        }
    }
}
 
void printOptimalParents(int s[][N],int i,int j)//������Ž⺯��
{
    if(i==j)
        cout<<"A"<<i;
    else
    {
        cout<<"(";
        printOptimalParents(s,i,s[i][j]);
        printOptimalParents(s,s[i][j]+1,j);
        cout<<")";
    }
}
 
int main()
{
    int p[N]={3,5,4,6,7,8,3};
    int m[N][N]={0},s[N][N]={0};
    matrixChainOrder(p,N,m,s);
    cout<<"m�����ֵΪ"<<endl;
    for(int i=1;i<N;++i)
    {
        for(int j=1;j<N;++j)
            cout<<m[i][j]<<"\t";
        cout<<endl;
    }
    cout<<"s�����ֵΪ"<<endl;
    for(int i=1;i<N;++i)
    {
        for(int j=1;j<N;++j)
            cout<<s[i][j]<<"\t";
        cout<<endl;
    }
    cout<<"�����������ŷ���˳������ʱ���������С"<<endl;
    printOptimalParents(s,1,N-1);
 
    return 0;
}
 