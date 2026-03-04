from io import StringIO

import pandas as pd
import requests
import ssl
from pymongo import MongoClient, UpdateOne

# Eğer hala SSL hatası alıyorsan bu kalsın, almıyorsan silebilirsin
ssl._create_default_https_context = ssl._create_unverified_context

conn_str = "mongodb://localhost:27017"
db_name = "SPX500"
table_name = "Companies"

def import_spx500():
    url = "https://en.wikipedia.org/wiki/List_of_S%26P_500_companies"

    # Kendimizi tarayıcı olarak tanıtıyoruz
    headers = {
        "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
    }

    try:
        # Sayfayı requests ile çekiyoruz
        response = requests.get(url, headers=headers)

        # Eğer sayfa başarıyla çekildiyse (200 OK)
        if response.status_code == 200:
            # Pandas'a sayfa içeriğini (text) gönderiyoruz
            response_text = StringIO(response.text)
            df = pd.read_html(response_text)[0]

            # Sütun isimlerini düzenleme kısmın buradan devam etsin...
            print("Veri başarıyla çekildi, MongoDB'ye aktarılıyor...")

            # --- Buradan aşağısı senin mevcut kolon düzenleme kodların ---
            column_names = {
                'Symbol': 'symbol',
                'Security': 'security',
                'GICS Sector': 'sector',
                'GICS Sub-Industry': 'sub_industry',
                'Headquarters Location': 'headquarters',
                'Date added': 'date_added',
                'CIK': 'cik',
                'Founded': 'founded_date'
            }
            df = df.rename(columns=column_names)
            df = df[list(column_names.values())]
            df = df.fillna("")

            client = MongoClient(conn_str)
            db = client[db_name]
            collection = db[table_name]

            collection.delete_many({})

            #df.to_dict(orient="records") şunu ifade eder: Her satırı (row) bir sözlük (dict) yapar, tüm satırları da bir liste içinde döndürür.
            #Yani MongoDB’ye insert_many() için en uygun formatlardan biridir.

            # [
            #     {"symbol": "AAPL", "security": "Apple Inc.", ...},
            #     {"symbol": "MSFT", "security": "Microsoft", ...},
            # ]

            companies_dict = df.to_dict(orient='records')
            collection.insert_many(companies_dict)

        else:
            print(f"Hata oluştu! Durum kodu: {response.status_code}")

    except Exception as e:
        print(f"Bağlantı sırasında bir hata oluştu: {e}")

def update_spx500():
    client = MongoClient(conn_str)
    db = client[db_name]
    collection = db[table_name]

    spx500_updated = [
        {'net_worth': '87.65B', 'symbol': 'MMM'}, {'net_worth': '9.19B', 'symbol': 'AOS'},
        {'net_worth': '203.3B', 'symbol': 'ABT'}, {'net_worth': '417.68B', 'symbol': 'ABBV'},
        {'net_worth': '126.72B', 'symbol': 'ACN'}, {'net_worth': '120.31B', 'symbol': 'ADBE'},
        {'net_worth': '311.33B', 'symbol': 'AMD'}, {'net_worth': '10.12B', 'symbol': 'AES'},
        {'net_worth': '58.54B', 'symbol': 'AFL'}, {'net_worth': '38.4B', 'symbol': 'A'},
        {'net_worth': '60.79B', 'symbol': 'APD'}, {'net_worth': '62.47B', 'symbol': 'ABNB'},
        {'net_worth': '14.15B', 'symbol': 'AKAM'}, {'net_worth': '20.99B', 'symbol': 'ALB'},
        {'net_worth': '16.8B', 'symbol': 'ARE'}, {'net_worth': '13.09B', 'symbol': 'ALGN'},
        {'net_worth': '13.99B', 'symbol': 'ALLE'}, {'net_worth': '18.60B', 'symbol': 'LNT'},
        {'net_worth': '48.2B', 'symbol': 'ALL'}, {'net_worth': '3.76T', 'symbol': 'GOOGL'},
        {'net_worth': '3.76T', 'symbol': 'GOOG'}, {'net_worth': '114.85B', 'symbol': 'MO'},
        {'net_worth': '2.34T', 'symbol': 'AMZN'}, {'net_worth': '15.4B', 'symbol': 'AMCR'},
        {'net_worth': '21.1B', 'symbol': 'AEE'}, {'net_worth': '52.3B', 'symbol': 'AEP'},
        {'net_worth': '221.5B', 'symbol': 'AXP'}, {'net_worth': '54.8B', 'symbol': 'AIG'},
        {'net_worth': '98.6B', 'symbol': 'AMT'}, {'net_worth': '22.4B', 'symbol': 'AWK'},
        {'net_worth': '39.7B', 'symbol': 'AMP'}, {'net_worth': '42.8B', 'symbol': 'AME'},
        {'net_worth': '204.1B', 'symbol': 'AMGN'}, {'net_worth': '165.42B', 'symbol': 'APH'},
        {'net_worth': '151.9B', 'symbol': 'ADI'}, {'net_worth': '68.2B', 'symbol': 'AON'},
        {'net_worth': '9.4B', 'symbol': 'APA'}, {'net_worth': '72.1B', 'symbol': 'APO'},
        {'net_worth': '3.91T', 'symbol': 'AAPL'}, {'net_worth': '255.88B', 'symbol': 'AMAT'},
        {'net_worth': '133.56B', 'symbol': 'APP'}, {'net_worth': '18.4B', 'symbol': 'APTV'},
        {'net_worth': '24.2B', 'symbol': 'ACGL'}, {'net_worth': '32.1B', 'symbol': 'ADM'},
        {'net_worth': '44.8B', 'symbol': 'ARES'}, {'net_worth': '165.0B', 'symbol': 'ANET'},
        {'net_worth': '61.4B', 'symbol': 'AJG'}, {'net_worth': '18.2B', 'symbol': 'AIZ'},
        {'net_worth': '206.5B', 'symbol': 'T'}, {'net_worth': '9.1B', 'symbol': 'ATO'},
        {'net_worth': '58.7B', 'symbol': 'ADSK'}, {'net_worth': '104.2B', 'symbol': 'ADP'},
        {'net_worth': '39.8B', 'symbol': 'AZO'}, {'net_worth': '30.4B', 'symbol': 'AVB'},
        {'net_worth': '16.9B', 'symbol': 'AVY'}, {'net_worth': '34.2B', 'symbol': 'AXON'},
        {'net_worth': '36.8B', 'symbol': 'BKR'}, {'net_worth': '14.2B', 'symbol': 'BALL'},
        {'net_worth': '364.9B', 'symbol': 'BAC'}, {'net_worth': '17.4B', 'symbol': 'BAX'},
        {'net_worth': '68.5B', 'symbol': 'BDX'}, {'net_worth': '1.04T', 'symbol': 'BRK.B'},
        {'net_worth': '21.5B', 'symbol': 'BBY'}, {'net_worth': '13.2B', 'symbol': 'TECH'},
        {'net_worth': '31.4B', 'symbol': 'BIIB'}, {'net_worth': '186.4B', 'symbol': 'BLK'},
        {'net_worth': '158.2B', 'symbol': 'BX'}, {'net_worth': '0B', 'symbol': 'XYZ'},  # Geçersiz sembol
        {'net_worth': '45.1B', 'symbol': 'BK'}, {'net_worth': '128.5B', 'symbol': 'BA'},
        {'net_worth': '142.3B', 'symbol': 'BKNG'}, {'net_worth': '124.7B', 'symbol': 'BSX'},
        {'net_worth': '112.5B', 'symbol': 'BMY'}, {'net_worth': '1.51T', 'symbol': 'AVGO'},
        {'net_worth': '22.8B', 'symbol': 'BR'}, {'net_worth': '28.4B', 'symbol': 'BRO'},
        {'net_worth': '20.1B', 'symbol': 'BF.B'}, {'net_worth': '18.9B', 'symbol': 'BLDR'},
        {'net_worth': '12.4B', 'symbol': 'BG'}, {'net_worth': '11.5B', 'symbol': 'BXP'},
        {'net_worth': '6.8B', 'symbol': 'CHRW'}, {'net_worth': '92.4B', 'symbol': 'CDNS'},
        {'net_worth': '13.1B', 'symbol': 'CPT'}, {'net_worth': '12.8B', 'symbol': 'CPB'},
        {'net_worth': '74.2B', 'symbol': 'COF'}, {'net_worth': '28.9B', 'symbol': 'CAH'},
        {'net_worth': '21.4B', 'symbol': 'CCL'}, {'net_worth': '68.7B', 'symbol': 'CARR'},
        {'net_worth': '35.4B', 'symbol': 'CVNA'}, {'net_worth': '184.2B', 'symbol': 'CAT'},
        {'net_worth': '19.5B', 'symbol': 'CBOE'}, {'net_worth': '42.1B', 'symbol': 'CBRE'},
        {'net_worth': '31.2B', 'symbol': 'CDW'}, {'net_worth': '48.9B', 'symbol': 'COR'},
        {'net_worth': '22.4B', 'symbol': 'CNC'}, {'net_worth': '18.7B', 'symbol': 'CNP'},
        {'net_worth': '16.4B', 'symbol': 'CF'}, {'net_worth': '11.2B', 'symbol': 'CRL'},
        {'net_worth': '144.5B', 'symbol': 'SCHW'}, {'net_worth': '48.9B', 'symbol': 'CHTR'},
        {'net_worth': '312.4B', 'symbol': 'CVX'}, {'net_worth': '82.5B', 'symbol': 'CMG'},
        {'net_worth': '94.8B', 'symbol': 'CB'}, {'net_worth': '24.1B', 'symbol': 'CHD'},
        {'net_worth': '7.8B', 'symbol': 'CIEN'}, {'net_worth': '164.2B', 'symbol': 'CI'},
        {'net_worth': '18.4B', 'symbol': 'CINF'}, {'net_worth': '86.2B', 'symbol': 'CTAS'},
        {'net_worth': '218.4B', 'symbol': 'CSCO'}, {'net_worth': '114.5B', 'symbol': 'C'},
        {'net_worth': '15.2B', 'symbol': 'CFG'}, {'net_worth': '18.9B', 'symbol': 'CLX'},
        {'net_worth': '78.4B', 'symbol': 'CME'}, {'net_worth': '19.2B', 'symbol': 'CMS'},
        {'net_worth': '278.4B', 'symbol': 'KO'}, {'net_worth': '38.2B', 'symbol': 'CTSH'},
        {'net_worth': '72.4B', 'symbol': 'COIN'}, {'net_worth': '82.1B', 'symbol': 'CL'},
        {'net_worth': '184.5B', 'symbol': 'CMCSA'}, {'net_worth': '12.4B', 'symbol': 'FIX'},
        {'net_worth': '14.2B', 'symbol': 'CAG'}, {'net_worth': '128.4B', 'symbol': 'COP'},
        {'net_worth': '36.2B', 'symbol': 'ED'}, {'net_worth': '44.8B', 'symbol': 'STZ'},
        {'net_worth': '92.4B', 'symbol': 'CEG'}, {'net_worth': '11.8B', 'symbol': 'COO'},
        {'net_worth': '42.1B', 'symbol': 'CPRT'}, {'net_worth': '38.4B', 'symbol': 'GLW'},
        {'net_worth': '18.2B', 'symbol': 'CPAY'}, {'net_worth': '36.4B', 'symbol': 'CTVA'},
        {'net_worth': '28.1B', 'symbol': 'CSGP'}, {'net_worth': '402.4B', 'symbol': 'COST'},
        {'net_worth': '18.2B', 'symbol': 'CTRA'}, {'net_worth': '72.4B', 'symbol': 'CRH'},
        {'net_worth': '84.2B', 'symbol': 'CRWD'}, {'net_worth': '46.1B', 'symbol': 'CCI'},
        {'net_worth': '74.2B', 'symbol': 'CSX'}, {'net_worth': '48.9B', 'symbol': 'CMI'},
        {'net_worth': '72.4B', 'symbol': 'CVS'}, {'net_worth': '194.5B', 'symbol': 'DHR'},
        {'net_worth': '20.4B', 'symbol': 'DRI'}, {'net_worth': '48.2B', 'symbol': 'DDOG'},
        {'net_worth': '11.4B', 'symbol': 'DVA'}, {'net_worth': '38.2B', 'symbol': 'DECK'},
        {'net_worth': '118.4B', 'symbol': 'DE'}, {'net_worth': '104.2B', 'symbol': 'DELL'},
        {'net_worth': '42.1B', 'symbol': 'DAL'}, {'net_worth': '24.8B', 'symbol': 'DVN'},
        {'net_worth': '28.4B', 'symbol': 'DXCM'}, {'net_worth': '22.1B', 'symbol': 'FANG'},
        {'net_worth': '48.2B', 'symbol': 'DLR'}, {'net_worth': '16.4B', 'symbol': 'DG'},
        {'net_worth': '12.1B', 'symbol': 'DLTR'}, {'net_worth': '62.4B', 'symbol': 'D'},
        {'net_worth': '14.2B', 'symbol': 'DPZ'}, {'net_worth': '42.8B', 'symbol': 'DASH'},
        {'net_worth': '24.1B', 'symbol': 'DOV'}, {'net_worth': '44.2B', 'symbol': 'DOW'},
        {'net_worth': '58.4B', 'symbol': 'DHI'}, {'net_worth': '24.1B', 'symbol': 'DTE'},
        {'net_worth': '92.4B', 'symbol': 'DUK'}, {'net_worth': '38.2B', 'symbol': 'DD'},
        {'net_worth': '142.4B', 'symbol': 'ETN'}, {'net_worth': '24.1B', 'symbol': 'EBAY'},
        {'net_worth': '68.4B', 'symbol': 'ECL'}, {'net_worth': '32.1B', 'symbol': 'EIX'},
        {'net_worth': '48.2B', 'symbol': 'EW'}, {'net_worth': '42.1B', 'symbol': 'EA'},
        {'net_worth': '104.5B', 'symbol': 'ELV'}, {'net_worth': '16.2B', 'symbol': 'EME'},
        {'net_worth': '74.2B', 'symbol': 'EMR'}, {'net_worth': '24.1B', 'symbol': 'ETR'},
        {'net_worth': '82.4B', 'symbol': 'EOG'}, {'net_worth': '12.1B', 'symbol': 'EPAM'},
        {'net_worth': '18.4B', 'symbol': 'EQT'}, {'net_worth': '31.2B', 'symbol': 'EFX'},
        {'net_worth': '84.2B', 'symbol': 'EQIX'}, {'net_worth': '24.1B', 'symbol': 'EQR'},
        {'net_worth': '11.4B', 'symbol': 'ERIE'}, {'net_worth': '16.2B', 'symbol': 'ESS'},
        {'net_worth': '24.1B', 'symbol': 'EL'}, {'net_worth': '18.4B', 'symbol': 'EG'},
        {'net_worth': '14.2B', 'symbol': 'EVRG'}, {'net_worth': '24.1B', 'symbol': 'ES'},
        {'net_worth': '38.2B', 'symbol': 'EXC'}, {'net_worth': '11.4B', 'symbol': 'EXE'},
        {'net_worth': '24.1B', 'symbol': 'EXPE'}, {'net_worth': '16.2B', 'symbol': 'EXPD'},
        {'net_worth': '28.4B', 'symbol': 'EXR'}, {'net_worth': '642.6B', 'symbol': 'XOM'},
        {'net_worth': '11.4B', 'symbol': 'FFIV'}, {'net_worth': '18.4B', 'symbol': 'FDS'},
        {'net_worth': '42.1B', 'symbol': 'FICO'}, {'net_worth': '44.2B', 'symbol': 'FAST'},
        {'net_worth': '11.4B', 'symbol': 'FRT'}, {'net_worth': '68.4B', 'symbol': 'FDX'},
        {'net_worth': '32.1B', 'symbol': 'FIS'}, {'net_worth': '14.2B', 'symbol': 'FITB'},
        {'net_worth': '11.4B', 'symbol': 'FSLR'}, {'net_worth': '16.2B', 'symbol': 'FE'},
        {'net_worth': '104.2B', 'symbol': 'FISV'}, {'net_worth': '48.2B', 'symbol': 'F'},
        {'net_worth': '88.4B', 'symbol': 'FTNT'}, {'net_worth': '28.4B', 'symbol': 'FTV'},
        {'net_worth': '16.2B', 'symbol': 'FOXA'}, {'net_worth': '14.2B', 'symbol': 'FOX'},
        {'net_worth': '10.4B', 'symbol': 'BEN'}, {'net_worth': '64.2B', 'symbol': 'FCX'},
        {'net_worth': '32.1B', 'symbol': 'GRMN'}, {'net_worth': '48.2B', 'symbol': 'IT'},
        {'net_worth': '212.4B', 'symbol': 'GE'}, {'net_worth': '84.2B', 'symbol': 'GEHC'},
        {'net_worth': '92.4B', 'symbol': 'GEV'}, {'net_worth': '11.4B', 'symbol': 'GEN'},
        {'net_worth': '14.2B', 'symbol': 'GNRC'}, {'net_worth': '84.2B', 'symbol': 'GD'},
        {'net_worth': '38.2B', 'symbol': 'GIS'}, {'net_worth': '74.2B', 'symbol': 'GM'},
        {'net_worth': '18.4B', 'symbol': 'GPC'}, {'net_worth': '112.4B', 'symbol': 'GILD'},
        {'net_worth': '32.1B', 'symbol': 'GPN'}, {'net_worth': '14.2B', 'symbol': 'GL'},
        {'net_worth': '28.4B', 'symbol': 'GDDY'}, {'net_worth': '184.2B', 'symbol': 'GS'},
        {'net_worth': '24.1B', 'symbol': 'HAL'}, {'net_worth': '32.1B', 'symbol': 'HIG'},
        {'net_worth': '8.4B', 'symbol': 'HAS'}, {'net_worth': '92.4B', 'symbol': 'HCA'},
        {'net_worth': '12.1B', 'symbol': 'DOC'}, {'net_worth': '11.4B', 'symbol': 'HSIC'},
        {'net_worth': '42.1B', 'symbol': 'HSY'}, {'net_worth': '28.4B', 'symbol': 'HPE'},
        {'net_worth': '58.4B', 'symbol': 'HLT'}, {'net_worth': '16.2B', 'symbol': 'HOLX'},
        {'net_worth': '412.4B', 'symbol': 'HD'}, {'net_worth': '142.4B', 'symbol': 'HON'},
        {'net_worth': '18.4B', 'symbol': 'HRL'}, {'net_worth': '12.1B', 'symbol': 'HST'},
        {'net_worth': '44.2B', 'symbol': 'HWM'}, {'net_worth': '48.2B', 'symbol': 'HPQ'},
        {'net_worth': '24.1B', 'symbol': 'HUBB'}, {'net_worth': '32.1B', 'symbol': 'HUM'},
        {'net_worth': '11.4B', 'symbol': 'HBAN'}, {'net_worth': '14.2B', 'symbol': 'HII'},
        {'net_worth': '212.4B', 'symbol': 'IBM'}, {'net_worth': '16.2B', 'symbol': 'IEX'},
        {'net_worth': '38.2B', 'symbol': 'IDXX'}, {'net_worth': '84.2B', 'symbol': 'ITW'},
        {'net_worth': '11.4B', 'symbol': 'INCY'}, {'net_worth': '32.1B', 'symbol': 'IR'},
        {'net_worth': '14.2B', 'symbol': 'PODD'}, {'net_worth': '98.4B', 'symbol': 'INTC'},
        {'net_worth': '48.2B', 'symbol': 'IBKR'}, {'net_worth': '92.4B', 'symbol': 'ICE'},
        {'net_worth': '24.1B', 'symbol': 'IFF'}, {'net_worth': '18.4B', 'symbol': 'IP'},
        {'net_worth': '184.2B', 'symbol': 'INTU'}, {'net_worth': '242.4B', 'symbol': 'ISRG'},
        {'net_worth': '11.4B', 'symbol': 'IVZ'}, {'net_worth': '16.2B', 'symbol': 'INVH'},
        {'net_worth': '42.1B', 'symbol': 'IQV'}, {'net_worth': '32.1B', 'symbol': 'IRM'},
        {'net_worth': '14.2B', 'symbol': 'JBHT'}, {'net_worth': '18.4B', 'symbol': 'JBL'},
        {'net_worth': '14.2B', 'symbol': 'JKHY'}, {'net_worth': '16.2B', 'symbol': 'J'},
        {'net_worth': '394.2B', 'symbol': 'JNJ'}, {'net_worth': '42.1B', 'symbol': 'JCI'},
        {'net_worth': '628.4B', 'symbol': 'JPM'}, {'net_worth': '38.2B', 'symbol': 'KVUE'},
        {'net_worth': '48.2B', 'symbol': 'KDP'}, {'net_worth': '16.2B', 'symbol': 'KEY'},
        {'net_worth': '32.1B', 'symbol': 'KEYS'}, {'net_worth': '48.2B', 'symbol': 'KMB'},
        {'net_worth': '11.4B', 'symbol': 'KIM'}, {'net_worth': '58.4B', 'symbol': 'KMI'},
        {'net_worth': '112.4B', 'symbol': 'KKR'}, {'net_worth': '118.4B', 'symbol': 'KLAC'},
        {'net_worth': '38.2B', 'symbol': 'KHC'}, {'net_worth': '36.4B', 'symbol': 'KR'},
        {'net_worth': '44.2B', 'symbol': 'LHX'}, {'net_worth': '18.4B', 'symbol': 'LH'},
        {'net_worth': '142.4B', 'symbol': 'LRCX'}, {'net_worth': '11.4B', 'symbol': 'LW'},
        {'net_worth': '24.1B', 'symbol': 'LVS'}, {'net_worth': '18.4B', 'symbol': 'LDOS'},
        {'net_worth': '38.2B', 'symbol': 'LEN'}, {'net_worth': '16.2B', 'symbol': 'LII'},
        {'net_worth': '912.4B', 'symbol': 'LLY'}, {'net_worth': '212.4B', 'symbol': 'LIN'},
        {'net_worth': '18.4B', 'symbol': 'LYV'}, {'net_worth': '124.2B', 'symbol': 'LMT'},
        {'net_worth': '14.2B', 'symbol': 'L'}, {'net_worth': '148.4B', 'symbol': 'LOW'},
        {'net_worth': '42.1B', 'symbol': 'LULU'}, {'net_worth': '32.1B', 'symbol': 'LYB'},
        {'net_worth': '18.4B', 'symbol': 'MTB'}, {'net_worth': '74.2B', 'symbol': 'MPC'},
        {'net_worth': '82.4B', 'symbol': 'MAR'}, {'net_worth': '112.4B', 'symbol': 'MRSH'},
        {'net_worth': '18.4B', 'symbol': 'MLM'}, {'net_worth': '14.2B', 'symbol': 'MAS'},
        {'net_worth': '482.4B', 'symbol': 'MA'}, {'net_worth': '11.4B', 'symbol': 'MTCH'},
        {'net_worth': '22.1B', 'symbol': 'MKC'}, {'net_worth': '194.2B', 'symbol': 'MCD'},
        {'net_worth': '78.4B', 'symbol': 'MCK'}, {'net_worth': '112.4B', 'symbol': 'MDT'},
        {'net_worth': '312.4B', 'symbol': 'MRK'}, {'net_worth': '1.85T', 'symbol': 'META'},
        {'net_worth': '48.2B', 'symbol': 'MET'}, {'net_worth': '32.1B', 'symbol': 'MTD'},
        {'net_worth': '14.2B', 'symbol': 'MGM'}, {'net_worth': '42.1B', 'symbol': 'MCHP'},
        {'net_worth': '124.4B', 'symbol': 'MU'}, {'net_worth': '3.15T', 'symbol': 'MSFT'},
        {'net_worth': '16.2B', 'symbol': 'MAA'}, {'net_worth': '48.2B', 'symbol': 'MRNA'},
        {'net_worth': '22.1B', 'symbol': 'MOH'}, {'net_worth': '14.2B', 'symbol': 'TAP'},
        {'net_worth': '92.4B', 'symbol': 'MDLZ'}, {'net_worth': '38.2B', 'symbol': 'MPWR'},
        {'net_worth': '58.4B', 'symbol': 'MNST'}, {'net_worth': '82.4B', 'symbol': 'MCO'},
        {'net_worth': '184.2B', 'symbol': 'MS'}, {'net_worth': '11.4B', 'symbol': 'MOS'},
        {'net_worth': '62.4B', 'symbol': 'MSI'}, {'net_worth': '48.2B', 'symbol': 'MSCI'},
        {'net_worth': '32.1B', 'symbol': 'NDAQ'}, {'net_worth': '24.1B', 'symbol': 'NTAP'},
        {'net_worth': '412.4B', 'symbol': 'NFLX'}, {'net_worth': '58.4B', 'symbol': 'NEM'},
        {'net_worth': '14.2B', 'symbol': 'NWSA'}, {'net_worth': '14.2B', 'symbol': 'NWS'},
        {'net_worth': '184.2B', 'symbol': 'NEE'}, {'net_worth': '122.4B', 'symbol': 'NKE'},
        {'net_worth': '11.4B', 'symbol': 'NI'}, {'net_worth': '14.2B', 'symbol': 'NDSN'},
        {'net_worth': '48.2B', 'symbol': 'NSC'}, {'net_worth': '18.4B', 'symbol': 'NTRS'},
        {'net_worth': '78.4B', 'symbol': 'NOC'}, {'net_worth': '14.2B', 'symbol': 'NCLH'},
        {'net_worth': '32.1B', 'symbol': 'NRG'}, {'net_worth': '52.4B', 'symbol': 'NUE'},
        {'net_worth': '4.78T', 'symbol': 'NVDA'}, {'net_worth': '18.4B', 'symbol': 'NVR'},
        {'net_worth': '82.4B', 'symbol': 'NXPI'}, {'net_worth': '64.2B', 'symbol': 'ORLY'},
        {'net_worth': '48.2B', 'symbol': 'OXY'}, {'net_worth': '42.1B', 'symbol': 'ODFL'},
        {'net_worth': '16.2B', 'symbol': 'OMC'}, {'net_worth': '32.1B', 'symbol': 'ON'},
        {'net_worth': '48.2B', 'symbol': 'OKE'}, {'net_worth': '492.4B', 'symbol': 'ORCL'},
        {'net_worth': '42.1B', 'symbol': 'OTIS'}, {'net_worth': '52.4B', 'symbol': 'PCAR'},
        {'net_worth': '18.4B', 'symbol': 'PKG'}, {'net_worth': '337.3B', 'symbol': 'PLTR'},
        {'net_worth': '118.4B', 'symbol': 'PANW'}, {'net_worth': '0B', 'symbol': 'PSKY'},
        {'net_worth': '72.4B', 'symbol': 'PH'}, {'net_worth': '42.1B', 'symbol': 'PAYX'},
        {'net_worth': '16.2B', 'symbol': 'PAYC'}, {'net_worth': '104.2B', 'symbol': 'PYPL'},
        {'net_worth': '14.2B', 'symbol': 'PNR'}, {'net_worth': '242.4B', 'symbol': 'PEP'},
        {'net_worth': '184.2B', 'symbol': 'PFE'}, {'net_worth': '38.2B', 'symbol': 'PCG'},
        {'net_worth': '242.4B', 'symbol': 'PM'}, {'net_worth': '68.4B', 'symbol': 'PSX'},
        {'net_worth': '11.4B', 'symbol': 'PNW'}, {'net_worth': '72.4B', 'symbol': 'PNC'},
        {'net_worth': '14.2B', 'symbol': 'POOL'}, {'net_worth': '32.1B', 'symbol': 'PPG'},
        {'net_worth': '18.4B', 'symbol': 'PPL'}, {'net_worth': '16.2B', 'symbol': 'PFG'},
        {'net_worth': '412.4B', 'symbol': 'PG'}, {'net_worth': '124.2B', 'symbol': 'PGR'},
        {'net_worth': '112.4B', 'symbol': 'PLD'}, {'net_worth': '42.1B', 'symbol': 'PRU'},
        {'net_worth': '38.2B', 'symbol': 'PEG'}, {'net_worth': '22.1B', 'symbol': 'PTC'},
        {'net_worth': '62.4B', 'symbol': 'PSA'}, {'net_worth': '32.1B', 'symbol': 'PHM'},
        {'net_worth': '38.2B', 'symbol': 'PWR'}, {'net_worth': '182.4B', 'symbol': 'QCOM'},
        {'net_worth': '16.2B', 'symbol': 'DGX'}, {'net_worth': '0B', 'symbol': 'Q'},
        {'net_worth': '12.1B', 'symbol': 'RL'}, {'net_worth': '24.1B', 'symbol': 'RJF'},
        {'net_worth': '184.2B', 'symbol': 'RTX'}, {'net_worth': '42.1B', 'symbol': 'O'},
        {'net_worth': '14.2B', 'symbol': 'REG'}, {'net_worth': '118.4B', 'symbol': 'REGN'},
        {'net_worth': '16.2B', 'symbol': 'RF'}, {'net_worth': '38.2B', 'symbol': 'RSG'},
        {'net_worth': '32.1B', 'symbol': 'RMD'}, {'net_worth': '14.2B', 'symbol': 'RVTY'},
        {'net_worth': '38.4B', 'symbol': 'HOOD'}, {'net_worth': '32.1B', 'symbol': 'ROK'},
        {'net_worth': '14.2B', 'symbol': 'ROL'}, {'net_worth': '58.4B', 'symbol': 'ROP'},
        {'net_worth': '48.2B', 'symbol': 'ROST'}, {'net_worth': '62.4B', 'symbol': 'RCL'},
        {'net_worth': '142.4B', 'symbol': 'SPGI'}, {'net_worth': '284.2B', 'symbol': 'CRM'},
        {'net_worth': '0B', 'symbol': 'SNDK'}, {'net_worth': '24.1B', 'symbol': 'SBAC'},
        {'net_worth': '62.4B', 'symbol': 'SLB'}, {'net_worth': '18.4B', 'symbol': 'STX'},
        {'net_worth': '48.2B', 'symbol': 'SRE'}, {'net_worth': '212.4B', 'symbol': 'NOW'},
        {'net_worth': '92.4B', 'symbol': 'SHW'}, {'net_worth': '58.4B', 'symbol': 'SPG'},
        {'net_worth': '14.2B', 'symbol': 'SWKS'}, {'net_worth': '14.2B', 'symbol': 'SJM'},
        {'net_worth': '0B', 'symbol': 'SW'}, {'net_worth': '16.2B', 'symbol': 'SNA'},
        {'net_worth': '18.4B', 'symbol': 'SOLV'}, {'net_worth': '104.2B', 'symbol': 'SO'},
        {'net_worth': '16.2B', 'symbol': 'LUV'}, {'net_worth': '12.1B', 'symbol': 'SWK'},
        {'net_worth': '112.4B', 'symbol': 'SBUX'}, {'net_worth': '28.4B', 'symbol': 'STT'},
        {'net_worth': '18.4B', 'symbol': 'STLD'}, {'net_worth': '32.1B', 'symbol': 'STE'},
        {'net_worth': '142.4B', 'symbol': 'SYK'}, {'net_worth': '24.2B', 'symbol': 'SMCI'},
        {'net_worth': '18.4B', 'symbol': 'SYF'}, {'net_worth': '88.4B', 'symbol': 'SNPS'},
        {'net_worth': '38.2B', 'symbol': 'SYY'}, {'net_worth': '242.4B', 'symbol': 'TMUS'},
        {'net_worth': '24.1B', 'symbol': 'TROW'}, {'net_worth': '28.4B', 'symbol': 'TTWO'},
        {'net_worth': '11.4B', 'symbol': 'TPR'}, {'net_worth': '38.2B', 'symbol': 'TRGP'},
        {'net_worth': '78.4B', 'symbol': 'TGT'}, {'net_worth': '62.4B', 'symbol': 'TEL'},
        {'net_worth': '32.1B', 'symbol': 'TDY'}, {'net_worth': '18.4B', 'symbol': 'TER'},
        {'net_worth': '1.33T', 'symbol': 'TSLA'}, {'net_worth': '184.2B', 'symbol': 'TXN'},
        {'net_worth': '22.1B', 'symbol': 'TPL'}, {'net_worth': '14.2B', 'symbol': 'TXT'},
        {'net_worth': '212.4B', 'symbol': 'TMO'}, {'net_worth': '124.2B', 'symbol': 'TJX'},
        {'net_worth': '18.4B', 'symbol': 'TKO'}, {'net_worth': '58.4B', 'symbol': 'TTD'},
        {'net_worth': '32.1B', 'symbol': 'TSCO'}, {'net_worth': '84.2B', 'symbol': 'TT'},
        {'net_worth': '82.4B', 'symbol': 'TDG'}, {'net_worth': '72.4B', 'symbol': 'TRV'},
        {'net_worth': '14.2B', 'symbol': 'TRMB'}, {'net_worth': '62.4B', 'symbol': 'TFC'},
        {'net_worth': '24.1B', 'symbol': 'TYL'}, {'net_worth': '18.4B', 'symbol': 'TSN'},
        {'net_worth': '72.4B', 'symbol': 'USB'}, {'net_worth': '162.4B', 'symbol': 'UBER'},
        {'net_worth': '12.1B', 'symbol': 'UDR'}, {'net_worth': '21.4B', 'symbol': 'ULTA'},
        {'net_worth': '164.2B', 'symbol': 'UNP'}, {'net_worth': '28.4B', 'symbol': 'UAL'},
        {'net_worth': '158.4B', 'symbol': 'UPS'}, {'net_worth': '54.2B', 'symbol': 'URI'},
        {'net_worth': '512.4B', 'symbol': 'UNH'}, {'net_worth': '18.4B', 'symbol': 'UHS'},
        {'net_worth': '38.2B', 'symbol': 'VLO'}, {'net_worth': '24.1B', 'symbol': 'VTR'},
        {'net_worth': '42.1B', 'symbol': 'VLTO'}, {'net_worth': '18.4B', 'symbol': 'VRSN'},
        {'net_worth': '42.1B', 'symbol': 'VRSK'}, {'net_worth': '168.4B', 'symbol': 'VZ'},
        {'net_worth': '112.4B', 'symbol': 'VRTX'}, {'net_worth': '12.1B', 'symbol': 'VTRS'},
        {'net_worth': '34.2B', 'symbol': 'VICI'}, {'net_worth': '624.4B', 'symbol': 'V'},
        {'net_worth': '92.4B', 'symbol': 'VST'}, {'net_worth': '32.1B', 'symbol': 'VMC'},
        {'net_worth': '14.2B', 'symbol': 'WRB'}, {'net_worth': '58.4B', 'symbol': 'GWW'},
        {'net_worth': '32.1B', 'symbol': 'WAB'}, {'net_worth': '1.01T', 'symbol': 'WMT'},
        {'net_worth': '194.2B', 'symbol': 'DIS'}, {'net_worth': '28.4B', 'symbol': 'WBD'},
        {'net_worth': '82.4B', 'symbol': 'WM'}, {'net_worth': '24.1B', 'symbol': 'WAT'},
        {'net_worth': '32.1B', 'symbol': 'WEC'}, {'net_worth': '284.2B', 'symbol': 'WFC'},
        {'net_worth': '74.2B', 'symbol': 'WELL'}, {'net_worth': '28.4B', 'symbol': 'WST'},
        {'net_worth': '16.2B', 'symbol': 'WDC'}, {'net_worth': '24.1B', 'symbol': 'WY'},
        {'net_worth': '14.2B', 'symbol': 'WSM'}, {'net_worth': '72.4B', 'symbol': 'WMB'},
        {'net_worth': '32.1B', 'symbol': 'WTW'}, {'net_worth': '68.4B', 'symbol': 'WDAY'},
        {'net_worth': '11.4B', 'symbol': 'WYNN'}, {'net_worth': '36.2B', 'symbol': 'XEL'},
        {'net_worth': '32.1B', 'symbol': 'XYL'}, {'net_worth': '38.2B', 'symbol': 'YUM'},
        {'net_worth': '24.1B', 'symbol': 'ZBRA'}, {'net_worth': '28.4B', 'symbol': 'ZBH'},
        {'net_worth': '84.2B', 'symbol': 'ZTS'}
    ]

    updates = []

    for company in spx500_updated:
        updates.append(
            UpdateOne(
                { "symbol": company["symbol"] },
                { "$set": { "net_worth": company["net_worth"] } },
                upsert=True
            )
        )

    collection.bulk_write(updates)


if __name__ == "__main__":
    update_spx500()
    #import_spx500()