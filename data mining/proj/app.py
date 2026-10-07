"""
app.py
------
Loan Approval Prediction – Streamlit Demo Application

Run with:
    streamlit run app.py

Place your model files inside the `models/` folder before running.
"""

import streamlit as st
import pandas as pd
import numpy as np

# Local utility modules
from utils.loader import load_all_artifacts, list_models_dir
from utils.preprocessing import preprocess
from utils.prediction import predict

# ── Page Configuration ────────────────────────────────────────────────────────
st.set_page_config(
    page_title="Loan Approval Prediction",
    page_icon="🏦",
    layout="wide",
    initial_sidebar_state="collapsed",
)

# ── Custom CSS ────────────────────────────────────────────────────────────────
st.markdown(
    """
    <style>
        /* Header */
        .main-header {
            background: linear-gradient(135deg, #1a3c6e 0%, #2563a8 100%);
            padding: 2rem 2.5rem;
            border-radius: 12px;
            margin-bottom: 1.5rem;
            color: white;
        }
        .main-header h1 { margin: 0; font-size: 2rem; font-weight: 700; }
        .main-header p  { margin: 0.4rem 0 0; opacity: 0.85; font-size: 1rem; }

        /* Section cards */
        .section-card {
            background: #f8faff;
            border: 1px solid #dbe4f0;
            border-radius: 10px;
            padding: 1.5rem 2rem;
            margin-bottom: 1.5rem;
        }
        .section-title {
            font-size: 1.1rem;
            font-weight: 700;
            color: #1a3c6e;
            margin-bottom: 1rem;
            padding-bottom: 0.4rem;
            border-bottom: 2px solid #2563a8;
        }

        /* Result boxes */
        .result-approved {
            background: linear-gradient(135deg, #d4edda, #b8dfc5);
            border-left: 6px solid #28a745;
            border-radius: 8px;
            padding: 1.5rem 2rem;
            margin-top: 1rem;
        }
        .result-rejected {
            background: linear-gradient(135deg, #f8d7da, #f5bec3);
            border-left: 6px solid #dc3545;
            border-radius: 8px;
            padding: 1.5rem 2rem;
            margin-top: 1rem;
        }
        .result-label {
            font-size: 2rem;
            font-weight: 800;
            margin: 0;
        }
        .result-sub {
            font-size: 1rem;
            margin-top: 0.3rem;
            opacity: 0.75;
        }
        /* Override Streamlit button default color */
        div[data-testid="stFormSubmitButton"] > button {
            background-color: #2563a8;
            color: white;
            border-radius: 8px;
            padding: 0.6rem 2rem;
            font-size: 1rem;
            font-weight: 600;
            border: none;
            width: 100%;
        }
        div[data-testid="stFormSubmitButton"] > button:hover {
            background-color: #1a3c6e;
            border: none;
        }
    </style>
    """,
    unsafe_allow_html=True,
)


# ── Header ────────────────────────────────────────────────────────────────────
st.markdown(
    """
    <div class="main-header">
        <h1>🏦 Loan Approval Prediction</h1>
        <p>Machine Learning Demo — Data Mining Final Project · 2025</p>
    </div>
    """,
    unsafe_allow_html=True,
)

# ── Load Artifacts (cached) ───────────────────────────────────────────────────
artifacts = load_all_artifacts()

# Show critical errors (model not found) at the very top
if artifacts["model"] is None:
    st.error(
        "### ⚠️ ไม่สามารถโหลด Model ได้\n\n"
        + "\n".join(artifacts["errors"])
        + "\n\n**วิธีแก้ไข:** วางไฟล์ model ที่ export จาก Colab ไว้ในโฟลเดอร์ `models/` แล้ว refresh หน้าเว็บ"
    )
    st.info(
        "ไฟล์ที่ระบบรองรับ:\n"
        "- `best_model.pkl` หรือ `best_model.joblib`\n"
        "- `scaler.pkl` (optional)\n"
        "- `label_encoders.pkl` (optional)\n"
        "- `feature_columns.pkl` (optional)\n"
        "- `metadata.json` (optional)"
    )
    st.stop()  # Halt the rest of the page

# Show non-fatal warnings
for w in artifacts["errors"]:
    st.warning(w)


# ── Section 1: Project Overview ───────────────────────────────────────────────
with st.expander("📋 Project Overview", expanded=False):
    st.markdown(
        """
        ระบบนี้ใช้โมเดล Machine Learning เพื่อทำนายว่าผู้สมัครขอสินเชื่อจะได้รับ
        **การอนุมัติ (Approved)** หรือ **ปฏิเสธ (Rejected)** โดยอิงจากข้อมูลทางการเงินและส่วนบุคคล

        **Feature ที่ใช้ในการทำนาย:**
        | กลุ่ม | Feature |
        |---|---|
        | ข้อมูลส่วนตัว | จำนวนผู้ที่พึ่งพา, การศึกษา, อาชีพอิสระ |
        | ข้อมูลทางการเงิน | รายได้ต่อปี, วงเงินกู้, ระยะเวลาผ่อน, CIBIL Score |
        | สินทรัพย์ | ที่อยู่อาศัย, เชิงพาณิชย์, ของฟุ่มเฟือย, ในธนาคาร |
        | Feature ที่สร้างใหม่ | รวมสินทรัพย์, อัตราส่วนกู้ต่อรายได้, รายได้ต่อผู้พึ่งพา |
        """
    )

# ── Section 2: Input Form ─────────────────────────────────────────────────────
st.markdown('<div class="section-title">📝 กรอกข้อมูลผู้ขอสินเชื่อ</div>', unsafe_allow_html=True)

with st.form("loan_form"):
    col1, col2, col3 = st.columns(3)

    with col1:
        st.markdown("**👤 ข้อมูลส่วนตัว**")
        no_of_dependents = st.number_input(
            "จำนวนผู้ที่พึ่งพา (คน)",
            min_value=0, max_value=10, value=0, step=1,
            help="จำนวนคนที่พึ่งพาผู้สมัครทางการเงิน"
        )
        education = st.selectbox(
            "ระดับการศึกษา",
            options=["Graduate", "Not Graduate"],
            help="Graduate = จบปริญญา"
        )
        self_employed = st.selectbox(
            "ประกอบอาชีพอิสระ",
            options=["No", "Yes"],
            help="Yes = ทำธุรกิจส่วนตัว / ฟรีแลนซ์"
        )

    with col2:
        st.markdown("**💰 ข้อมูลทางการเงิน**")
        income_annum = st.number_input(
            "รายได้ต่อปี (บาท)",
            min_value=0, max_value=100_000_000, value=500_000, step=10_000,
            format="%d",
            help="รายได้รวมต่อปีก่อนหักภาษี"
        )
        loan_amount = st.number_input(
            "วงเงินกู้ที่ต้องการ (บาท)",
            min_value=0, max_value=500_000_000, value=1_000_000, step=10_000,
            format="%d",
            help="จำนวนเงินกู้ทั้งหมดที่ต้องการ"
        )
        loan_term = st.number_input(
            "ระยะเวลากู้ (ปี)",
            min_value=1, max_value=30, value=10, step=1,
            help="ระยะเวลาผ่อนชำระเป็นปี"
        )
        cibil_score = st.number_input(
            "CIBIL Score",
            min_value=300, max_value=900, value=700, step=1,
            help="คะแนนเครดิต 300–900 (ยิ่งสูงยิ่งดี)"
        )

    with col3:
        st.markdown("**🏠 มูลค่าสินทรัพย์ (บาท)**")
        residential_assets_value = st.number_input(
            "สินทรัพย์ที่อยู่อาศัย",
            min_value=0, max_value=500_000_000, value=1_000_000, step=10_000, format="%d"
        )
        commercial_assets_value = st.number_input(
            "สินทรัพย์เชิงพาณิชย์",
            min_value=0, max_value=500_000_000, value=0, step=10_000, format="%d"
        )
        luxury_assets_value = st.number_input(
            "สินทรัพย์ของฟุ่มเฟือย",
            min_value=0, max_value=500_000_000, value=0, step=10_000, format="%d"
        )
        bank_asset_value = st.number_input(
            "เงินในธนาคาร / กองทุน",
            min_value=0, max_value=500_000_000, value=200_000, step=10_000, format="%d"
        )

    st.markdown("---")
    submitted = st.form_submit_button("🔍 ทำนายผลการอนุมัติ (Predict)")


# ── Section 3: Validation ─────────────────────────────────────────────────────
validation_warnings = []
if submitted:
    if cibil_score < 300 or cibil_score > 900:
        validation_warnings.append("⚠️ CIBIL Score ที่ถูกต้องควรอยู่ระหว่าง 300–900")
    if income_annum == 0:
        validation_warnings.append("⚠️ รายได้ต่อปีเป็น 0 — อัตราส่วนบางค่าอาจไม่ถูกต้อง")
    if loan_amount > income_annum * 50:
        validation_warnings.append("⚠️ วงเงินกู้สูงผิดปกติเมื่อเทียบกับรายได้ (> 50× รายได้ต่อปี)")

    for w in validation_warnings:
        st.warning(w)


# ── Section 4: Input Summary ──────────────────────────────────────────────────
if submitted:
    st.markdown('<div class="section-title">📊 สรุปข้อมูลที่กรอก</div>', unsafe_allow_html=True)
    total_assets = (
        residential_assets_value + commercial_assets_value
        + luxury_assets_value + bank_asset_value
    )
    loan_to_income = loan_amount / income_annum if income_annum != 0 else 0
    income_per_dep = income_annum / (no_of_dependents + 1)

    summary_col1, summary_col2, summary_col3 = st.columns(3)
    with summary_col1:
        st.metric("รายได้ต่อปี", f"฿{income_annum:,.0f}")
        st.metric("วงเงินกู้", f"฿{loan_amount:,.0f}")
        st.metric("CIBIL Score", cibil_score)
    with summary_col2:
        st.metric("รวมสินทรัพย์", f"฿{total_assets:,.0f}")
        st.metric("อัตราส่วนกู้/รายได้", f"{loan_to_income:.2f}×")
        st.metric("รายได้ต่อผู้พึ่งพา", f"฿{income_per_dep:,.0f}")
    with summary_col3:
        st.metric("ระยะเวลากู้", f"{loan_term} ปี")
        st.metric("ผู้ที่พึ่งพา", f"{no_of_dependents} คน")
        st.metric("การศึกษา", education)


# ── Section 5: Prediction ─────────────────────────────────────────────────────
if submitted:
    st.markdown('<div class="section-title">🎯 ผลการทำนาย</div>', unsafe_allow_html=True)

    # Build raw input DataFrame
    raw_input = pd.DataFrame([{
        "no_of_dependents":          no_of_dependents,
        "education":                 education,
        "self_employed":             self_employed,
        "income_annum":              income_annum,
        "loan_amount":               loan_amount,
        "loan_term":                 loan_term,
        "cibil_score":               cibil_score,
        "residential_assets_value":  residential_assets_value,
        "commercial_assets_value":   commercial_assets_value,
        "luxury_assets_value":       luxury_assets_value,
        "bank_asset_value":          bank_asset_value,
    }])

    # Preprocess
    with st.spinner("กำลังประมวลผล..."):
        processed_df, preprocess_warnings = preprocess(
            raw_input,
            label_encoders=artifacts["label_encoders"],
            scaler=artifacts["scaler"],
            feature_columns=artifacts["feature_columns"],
        )

    for w in preprocess_warnings:
        st.warning(w)

    # Predict
    result = predict(artifacts["model"], processed_df)

    if result["error"]:
        st.error(f"### ❌ ทำนายไม่สำเร็จ\n\n{result['error']}")
    else:
        # Display result card
        is_approved = "Approved" in (result["label"] or "")
        card_class  = "result-approved" if is_approved else "result-rejected"
        st.markdown(
            f'<div class="{card_class}">'
            f'<p class="result-label">{result["label"]}</p>'
            f'<p class="result-sub">Raw prediction: {result["raw_prediction"]}</p>'
            f'</div>',
            unsafe_allow_html=True,
        )

        # Probability gauge (if available)
        if result["proba_approved"] is not None:
            st.markdown("#### ความน่าจะเป็น (Probability)")
            prob_col1, prob_col2 = st.columns(2)
            with prob_col1:
                st.metric(
                    "✅ โอกาสอนุมัติ (Approved)",
                    f"{result['proba_approved'] * 100:.1f}%"
                )
            with prob_col2:
                st.metric(
                    "❌ โอกาสปฏิเสธ (Rejected)",
                    f"{result['proba_rejected'] * 100:.1f}%"
                )
            st.progress(result["proba_approved"], text="Approval probability")

        # Processed feature table (collapsible)
        with st.expander("🔬 ดู Feature ที่ส่งให้ Model", expanded=False):
            st.dataframe(processed_df, use_container_width=True)


# ── Section 6: Model Information ─────────────────────────────────────────────
st.markdown("---")
st.markdown('<div class="section-title">ℹ️ ข้อมูล Model</div>', unsafe_allow_html=True)

info_col1, info_col2 = st.columns(2)

with info_col1:
    st.markdown("**ไฟล์ที่โหลดสำเร็จ:**")
    if artifacts["loaded_files"]:
        for f in artifacts["loaded_files"]:
            st.success(f"✅ `{f}`")
    else:
        st.info("ยังไม่มีไฟล์ที่โหลด")

    if artifacts["errors"]:
        st.markdown("**คำเตือน / ข้อผิดพลาด:**")
        for e in artifacts["errors"]:
            st.warning(e)

with info_col2:
    st.markdown("**ชื่อโมเดล:**")
    if artifacts["model"] is not None:
        model_name = type(artifacts["model"]).__name__
        st.code(model_name)

    if artifacts["metadata"]:
        st.markdown("**Metadata:**")
        st.json(artifacts["metadata"])

    st.markdown("**ไฟล์ทั้งหมดใน `models/`:**")
    model_files = list_models_dir()
    if model_files:
        st.code("\n".join(model_files))
    else:
        st.info("ไม่พบไฟล์ใน `models/`")
