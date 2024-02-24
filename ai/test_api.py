import os
import requests
import json
import re
import pymysql

# api 키 저장
import key

def getData(): 
    conn = pymysql.connect(
        host = 'localhost',
        user = '',
        password = '',
        db = '',
        charset = 'UTF-8',
    )
    
    curs = conn.cursor()
    
    sql = "COMMAND HERE"
    curs.execute(sql)
    result = curs.fetchall()
    print(result)

def summarize(reviewfile="null.txt", tone=3, summaryCount=5):
    
    client_id = key.client_id
    client_secret = key.client_secret
    url = 'https://naveropenapi.apigw.ntruss.com/text-summary/v1/summarize'
    
    reviewfile_path = os.getcwd() + '/ai/' + reviewfile
    with open(reviewfile_path, 'r', encoding='UTF-8') as file:
        content = file.read()

    tone = tone
    summaryCount = summaryCount
    
    headers = {
            'Accept': 'application/json;UTF-8',
            'Content-Type': 'application/json;UTF-8',
            'X-NCP-APIGW-API-KEY-ID': client_id,
            'X-NCP-APIGW-API-KEY': client_secret,
        }

    data = {
    "document": {
        "content": content
    },
    "option": {
        "language": "ko",
        "model": "general",
        "tone": tone,
        "summaryCount": summaryCount,
    },
    }

    response = requests.post(
        url, 
        headers = headers, 
        data = json.dumps(data).encode('UTF-8'))

    rescode = response.status_code
    if(rescode != 200):
        print("*** [Error] summarize : " + response.text)
        return # 에러 발생 시 현재 처리중인 텍스트 건너뛰기
        
    summary_raw = response.text
    summary = summary_raw.split(":")[1].lstrip("\"").rstrip("\"}")
    summaries = summary.split("\\n")
    for i, line in enumerate(summaries):
        summaries[i] = re.sub('[.,!?]', '', line)

    summaryfile_path = os.getcwd() + '/ai/' + 'out_summary_' + reviewfile
    with open(summaryfile_path, "w", encoding='UTF-8') as file:
        for line in summaries:
            file.write(line)
            file.write("\n")
        
def sentiment(reviewfile="null.txt"): 

    client_id = key.client_id
    client_secret = key.client_secret
    url = 'https://naveropenapi.apigw.ntruss.com/sentiment-analysis/v1/analyze'

    reviewfile_path = os.getcwd() + '/ai/' + reviewfile
    with open(reviewfile_path, 'r', encoding='UTF-8') as file:
        content = file.read()
        
        
    headers = {
            'Accept': 'application/json;UTF-8',
            'Content-Type': 'application/json;UTF-8',
            'X-NCP-APIGW-API-KEY-ID': client_id,
            'X-NCP-APIGW-API-KEY': client_secret,
        }
    
    data = {
        "content": content
    }
    
    json.dumps(data, indent=4, sort_keys=True)
    response = requests.post(
        url, 
        headers = headers, 
        data = json.dumps(data).encode('UTF-8'))

    rescode = response.status_code
    if(rescode != 200):
        print("*** [Error] sentiment : " + response.text)
        return # 에러 발생 시 현재 처리중인 텍스트 건너뛰기
        
    sentiment_raw = response.text
        
    sentimentfile_path = os.getcwd() + '/ai/' + 'out_sentiment_' + reviewfile
    with open(sentimentfile_path, "w", encoding='UTF-8') as file:
        file.write(sentiment_raw)